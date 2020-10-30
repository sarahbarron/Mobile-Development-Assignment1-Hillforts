package org.wit.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.helpers.*
import java.util.*

val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<HillfortModel>>() {}.type

// Generate a random ID for each hillfort
fun generateRandomId(): Long {
    return Random().nextLong()
}


class HillfortJSONStore : HillfortStore, AnkoLogger {

    val context: Context
    var hillforts = mutableListOf<HillfortModel>()

    //    Constructor checks if the file exists if it does it deserializes it
    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    //    Return a list of all hillforts
    override fun findAll(): MutableList<HillfortModel> {
        return hillforts
    }

    override fun findOne(hillfort: HillfortModel): HillfortModel{
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            return foundHillfort
        }
        return hillfort
    }
    //    Create a hillfort
    override fun create(hillfort: HillfortModel) {
        hillfort.id = generateRandomId()
        hillforts.add(hillfort)
        serialize()
    }

    // Update a hillfort
    override fun update(hillfort: HillfortModel) {

        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.name = hillfort.name
            foundHillfort.description = hillfort.description
            foundHillfort.images = hillfort.images
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            serialize()
        }
    }

    override fun visited(hillfort: HillfortModel, boolean: Boolean) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.visited = boolean
            serialize()
        }
    }

    override fun delete(hillfort: HillfortModel) {
        hillforts.remove(hillfort)
        serialize()
    }

//  Delete an image from a hillfort
    override fun deleteImage(hillfort: HillfortModel, image: String) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        foundHillfort?.images?.remove(image)
        serialize()
    }

    // Serialize / write data to the JSON file
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(hillforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    //    deserialize / read data from the JSON file
    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString, listType)
    }
}