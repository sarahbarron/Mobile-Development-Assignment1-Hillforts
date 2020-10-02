package org.wit.hillforts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class HillfortActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        info("Hillforts activity started...")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
    }
}