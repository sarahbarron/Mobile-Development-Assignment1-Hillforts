package org.wit.hillfort.models


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.helpers.*
import java.util.*

val USER_JSON_FILE = "users.json"
val Gson = GsonBuilder().setPrettyPrinting().create()
val USER_lIST_TYPE = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

// Generate a random ID for each hillfort
fun generateRandomUserId(): Long {
    return Random().nextLong()
}


class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    //    Constructor checks if the file exists if it does it deserializes it
    constructor (context: Context) {
        this.context = context
        if (exists(context, USER_JSON_FILE)) {
            userDeserialize()
        }
    }

    //    Return a list of all user
    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun findOne(user: UserModel): UserModel{
        var foundUser: UserModel? = users.find{ p -> p.id == user.id }
        if(foundUser != null) {
            return foundUser
        }
        return user
    }


    //    Create a user
    override fun create(user: UserModel) {
        user.id = generateRandomUserId()
        users.add(user)
        userSerialize()
        info("user saved")
    }

    // Update a user
    override fun update(user: UserModel) {

        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.password = user.password
            userSerialize()
        }
    }


    override fun delete(user: UserModel) {
        users.remove(user)
        userSerialize()
    }

    override fun authenticate(user: UserModel):Boolean {
        var foundUser: UserModel? = users.find{ p -> p.username == user.username }
        if(foundUser != null) {
            if (foundUser.password == user.password) {
                return true
            }
        }
        return false
    }


    // Serialize / write data to the JSON file
    private fun userSerialize() {
        val jsonString = Gson.toJson(users, USER_lIST_TYPE)
        write(context, USER_JSON_FILE, jsonString)
        info("userSerialize")
    }

    //    deserialize / read data from the JSON file
    private fun userDeserialize() {
        val jsonString = read(context, USER_JSON_FILE)
        users = Gson().fromJson(jsonString, USER_lIST_TYPE)
    }
}