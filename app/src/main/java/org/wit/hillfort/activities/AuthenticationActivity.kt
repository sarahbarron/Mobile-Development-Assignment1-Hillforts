package org.wit.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp

class AuthenticationActivity: AppCompatActivity(), AnkoLogger{

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        app = application as MainApp

    }
}