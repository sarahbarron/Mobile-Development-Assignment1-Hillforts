package org.wit.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authentication.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel

class AuthenticationActivity: AppCompatActivity(), AnkoLogger{

    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        app = application as MainApp

        btnAuthenticate.setOnClickListener(){
            user.username = username.text.toString()
            user.password = password.text.toString()

            if(user.username.isEmpty() && user.password.isEmpty())
            {
                toast("Please Enter a Username and Password")
            }
            else if(user.username.isEmpty() && user.password.isNotEmpty())
            {
                toast("Please Enter a Username")
            }
            else if(user.username.isNotEmpty() && user.password.isEmpty())
            {
                toast("Please Enter a Password")
            }
            else if(user.username.isNotEmpty() && user.password.isNotEmpty())
            {
                val isAuthenticated = app.users.authenticate(user.copy())
                if (isAuthenticated)
                {
                    info("logging in user")
                    startActivityForResult(intentFor<HillfortListActivity>().putExtra("user", user),0)
                }
                else
                {
                    info("authentication failed")
                    toast("Your username or password is incorrect please try again")
                }
            }
        }
    }
}