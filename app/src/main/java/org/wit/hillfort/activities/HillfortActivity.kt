package org.wit.hillforts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillforts.R
import kotlinx.android.synthetic.main.activity_hillfort.*

class HillfortActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        info("Hillforts activity started...")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        btnAdd.setOnClickListener() {
            info("add Button Pressed")
        }
    }


}