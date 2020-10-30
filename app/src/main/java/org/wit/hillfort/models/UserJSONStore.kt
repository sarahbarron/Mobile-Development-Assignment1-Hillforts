package org.wit.hillfort.models


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.helpers.*
import java.util.*

val USER_JSON = "hillforts.json"
val USER_Gson = GsonBuilder().setPrettyPrinting().create()
val USER_list = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

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
        if (exists(context, USER_JSON)) {
            deserialize()
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
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    // Update a user
    override fun update(user: UserModel) {

        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.password = user.password
            serialize()
        }
    }


    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }

    override fun authenticate(username: String, password: String) {
        TODO("Not yet implemented")
    }


    // Serialize / write data to the JSON file
    private fun serialize() {
        val jsonString = USER_Gson.toJson(users, USER_list)
        write(context, USER_JSON, jsonString)
    }

    //    deserialize / read data from the JSON file
    private fun deserialize() {
        val jsonString = read(context, USER_JSON)
        users = Gson().fromJson(jsonString, USER_list)
    }
}