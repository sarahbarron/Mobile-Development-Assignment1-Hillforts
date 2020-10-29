package org.wit.hillfort.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_image.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.R
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.main.MainApp

class ImageActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

//        toolbar.title = title
//        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        app = application as MainApp

       if(intent.hasExtra("image")) {
           var bitmap = readImageFromPath(this, intent.extras?.getString("image")!!)
           singleHillfortImage.setImageBitmap(bitmap)
       }
    }
}