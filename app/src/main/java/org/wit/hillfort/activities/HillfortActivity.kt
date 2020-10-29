package org.wit.hillfort.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.R
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.toast
import org.wit.hillfort.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillfort.hillfortName
import org.jetbrains.anko.intentFor
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.Location

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    var edit = false
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {

        info("Hillforts activity started...")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title=title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        //        Create a Linear layout manager & tell the recyclerView to use this layout manager
        val layoutManager = LinearLayoutManager(this)
        recyclerViewImages.layoutManager = layoutManager


        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!

            headingHillfortName.setText(hillfort.name)
            hillfortName.setText(hillfort.name)
            hillfortDescription.setText(hillfort.description)
            btnAdd.setText(R.string.save_hillfort)
            if (hillfort.images.size > 0 && hillfort.images != null) {
                showImages(hillfort.images)
                if(hillfort.images.size<4)
                {
                    chooseImage.setText(R.string.add_four_hillfort_image)
                }
                else{
                    chooseImage.setText(R.string.max_hillfort_images)
                }
            }

            if (hillfort.visited){
                visitedHillfort.isChecked = true
            }

        }

        chooseImage.setOnClickListener {
            info ("Select image")
            if(hillfort.images.size<4) {
                showImagePicker(this, IMAGE_REQUEST)
            }
            else
            {
                toast("Maximum number of images already saved")
            }
        }

        btnAdd.setOnClickListener() {
            hillfort.name = hillfortName.text.toString()
            info("Name: $hillfort.name")
            hillfort.description = hillfortDescription.text.toString()
            info("description : ${hillfort.description}")
            if (hillfort.name.isNotEmpty()) {

                if (edit) {
                    app.hillforts.update(hillfort.copy())
                    info("Edit: $hillfort")
                } else {
                    app.hillforts.create(hillfort.copy())
                    info("Create: $hillfort")
                }
            }
            else{
                toast(R.string.enter_hillfort_name)
            }
            info("Add Button Pressed: $hillfortName")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        hillfortLocation.setOnClickListener {
//            If the location placemark object's zoom is 0.0 use a default location
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.zoom != 0f) {
                location.lat =  hillfort.lat
                location.lng = hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.visitedHillfort -> {
                    if (checked) {
                        app.hillforts.visited(hillfort, true)
                    } else {
                        app.hillforts.visited(hillfort, false)
                    }
                }
            }
        }
    }



    fun showImages (images: ArrayList<String>) {
        recyclerViewImages.adapter = ImageAdapter(images)
        recyclerViewImages.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.hillforts.delete(hillfort.copy())
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    When the activity picker activity finishes retrieve the images name & display it
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    hillfort.images.add(data.getData().toString())
                    if(hillfort.images.size < 4) {
                        chooseImage.setText(R.string.add_four_hillfort_image)
                    }
                    else{
                        chooseImage.setText(R.string.max_hillfort_images)
                    }
                    showImages(hillfort.images)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                }
            }
        }
    }
}