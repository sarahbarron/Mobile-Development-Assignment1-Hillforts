package org.wit.hillfort.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.R
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.toast
import org.wit.hillfort.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillfort.hillfortName
import org.jetbrains.anko.intentFor
import org.wit.hillfort.helpers.readImage
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.Location

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    var edit = false
    val IMAGE_REQUEST = 1
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {

        info("Hillforts activity started...")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title=title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortName.setText(hillfort.name)
            hillfortDescription.setText(hillfort.description)
            btnAdd.setText(R.string.save_hillfort)
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            if(hillfort.image != null){
                chooseImage.setText(R.string.change_hillfort_image)
            }
        }

        chooseImage.setOnClickListener {
            info ("Select image")
            showImagePicker(this, IMAGE_REQUEST)
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
            val location = Location(51.245696, -6.139102, 15f)
            startActivity (intentFor<MapActivity>().putExtra("location", location))
        }
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
        }
        return super.onOptionsItemSelected(item)
    }

//    When the activity picker activity finishes retrieve the images name & display it
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    hillfort.image = data.getData().toString()
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_hillfort_image)
                }
            }
        }
    }
}