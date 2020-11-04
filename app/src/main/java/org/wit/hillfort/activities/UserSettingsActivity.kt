package org.wit.hillfort.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
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
            settingsUsername.setText(user.username)
            settingsPassword.setText(user.password)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_settingsCancel -> {
                finish()
            }
            R.id.item_deleteUser -> {
                app.users.delete(user.copy())
                startActivityForResult(intentFor<AuthenticationActivity>(),0)
            }
            R.id.item_logout -> {
                startActivityForResult(intentFor<AuthenticationActivity>(),0)
            }

        }
        return super.onOptionsItemSelected(item)
    }

}