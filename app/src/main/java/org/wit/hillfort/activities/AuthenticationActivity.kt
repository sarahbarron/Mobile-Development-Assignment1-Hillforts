package org.wit.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authentication.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel

class AuthenticationActivity: AppCompatActivity(), AnkoLogger{

    var user = UserModel()
    lateinit var app: MainApp
//    Regex for an email address
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        app = application as MainApp


//        Sign In Button Click Listener & authentication
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
                if (user.username.matches(emailPattern.toRegex())){
                    val isAuthenticated = app.users.authenticate(user.copy())
                    if (isAuthenticated) {
                        info("logging in user")
                        startActivityForResult(
                            intentFor<HillfortListActivity>().putExtra(
                                "user",
                                user
                            ), 0
                        )
                    } else {
                        val userIsRegistered = app.users.isUsernameRegistered(user.username)
                        if (userIsRegistered) {
                            longToast("Your password is incorrect please try again")
                        } else {
                            longToast("Please Register: no user registered with this username")
                        }

                        info("authentication failed")
                    }
                }
                else{
                    toast("Invalid  Email Address")
                }

            }
        }

        btnRegister.setOnClickListener(){

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
                if (user.username.matches(emailPattern.toRegex())){
                    val userIsRegistered = app.users.isUsernameRegistered(user.username)
                    if (userIsRegistered) {
                        longToast("Already Registered, please Sign In")
                        info("authentication failed invalid username")
                    } else {
                        user = app.users.create(user.copy())

                        info("$user created start HillfortListActivity")
                        startActivityForResult(
                            intentFor<HillfortListActivity>().putExtra(
                                "user",
                                user
                            ), 0
                        )
                    }
                }
                else{
                    toast("Invalid  Email Address")
                }
            }
        }
    }

//    Don't allow a user to go back from the authentication scree
    override fun onBackPressed() {
        longToast("You must Sign In or Register")
    }
}