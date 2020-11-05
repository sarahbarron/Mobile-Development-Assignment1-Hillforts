package org.wit.hillfort.activities

import android.graphics.Color.RED
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel


class UserSettingsActivity: AppCompatActivity(), AnkoLogger {
    var user = UserModel()
    lateinit var app : MainApp

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbarSettings.title=title
        setSupportActionBar(toolbarSettings)

        app = application as MainApp

        if(intent.hasExtra("user"))
        {
            user = intent.extras?.getParcelable<UserModel>("user")!!
            user = loadUser()
            settingsUsername.setText(user.username)
            settingsPassword.setText(user.password)

//            statistics
//            User statistics
            val userViewed = app.hillforts.totalHillforts(user.id)
            statisticsHillfortsTotal.setText("Total: $userViewed")
            statisticsHillfortsViewed.setText("Viewed: "+app.hillforts.viewedHillforts(user.id))
            statisticsHillfortsUnseen.setText("Unseen: "+app.hillforts.unseenHillforts(user.id))
            val classViewed = app.hillforts.classAverageViewed()
            statisticsClassViewed.setText("Average Viewed: $classViewed")
            statisticsClassUnseen.setText("Average Unseen: "+app.hillforts.classAverageUnseen())
            if(userViewed>=classViewed){ statisticsUserPosition.setText("Keep up the good work!!")}
            else {
                statisticsUserPosition.setText("Below average! Its time to catch up")
                statisticsUserPosition.setTextColor(RED)
            }
        }

//        Update user details
        btnUpdateSettings.setOnClickListener(){
            var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if(settingsUsername != null && settingsPassword !=null) {
                if (user.username.matches(emailPattern.toRegex())) {
                    user.username = settingsUsername.text.toString()
                    user.password = settingsPassword.text.toString()
                    app.users.update(user)
                }
                else{
                    longToast("Username must be your email address")
                }
            }
            else{
                toast("Enter a username and password")
            }
        }

//        Delete User
        btnDeleteUser.setOnClickListener(){
            app.hillforts.deleteUserHillforts(user.id)
            app.users.delete(user.copy())
            startActivityForResult(intentFor<AuthenticationActivity>(),0)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_settingsCancel -> {
                finish()
            }
            R.id.item_deleteUser -> {
                info("delete :"+user.id)
                app.hillforts.deleteUserHillforts(user.id)
                app.users.delete(user.copy())
                startActivityForResult(intentFor<AuthenticationActivity>(),0)
            }
            R.id.item_logout -> {
                startActivityForResult(intentFor<AuthenticationActivity>(),0)
            }

        }
        return super.onOptionsItemSelected(item)
    }
    //    retrieve the current hillfort from the JSON file
    fun loadUser(): UserModel{
        user = app.users.findOne(user.copy())
        return user
    }
}