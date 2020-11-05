package org.wit.hillfort.models
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L
internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(userId: Long): List<HillfortModel> {
        return hillforts
    }

    override fun findOne(hillfort: HillfortModel): HillfortModel{
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            return foundHillfort
        }
        return hillfort
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.name = hillfort.name
            foundHillfort.description = hillfort.description
            foundHillfort.images = hillfort.images
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            logAll()
        }
    }
    override fun visited(hillfort: HillfortModel, boolean: Boolean){
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if(foundHillfort !=null) {
            foundHillfort.visited = boolean
        }
    }

    override fun delete(hillfort: HillfortModel){
        hillforts.remove(hillfort)
    }

    override fun deleteUserHillforts(userId: Long) {
        for(hillfort in hillforts)
        {
            if(hillfort.user == userId)
            {
                hillforts.remove(hillfort.copy())
            }
        }
    }

    fun logAll() {
        hillforts.forEach{ info("${it}") }
    }

    override fun deleteImage(hillfort: HillfortModel, image: String) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        foundHillfort?.images?.remove(image)
    }

    //    Statistics

    //    Total number of hillforts a user has
    override fun totalHillforts(userId: Long): Int{
        val total = findAll(userId).size
        info("Total user hillforts: $total")
        return total
    }

    //    Total number of hillforts the user has viewed
    override fun viewedHillforts(userId: Long): Int{
        val foundHillforts = findAll(userId)
        var total = 0
        for (hillfort in foundHillforts)
        {
            if (hillfort.visited) {
                total++
            }
        }
        info("Average user visited: $total")
        return total
    }

    //  Total number of hillforts the user still has to view
    override fun unseenHillforts(userId:Long): Int{
        val total = totalHillforts(userId) - viewedHillforts(userId)
        info("Average user unseen: $total")
        return total
    }

    //    The average number of hillforts the class has viewed
    override fun classAverageViewed():Int{
        var totalViewed = 0
        for (hillfort in hillforts)
        {
            if(hillfort.visited)
            {
                totalViewed++
            }
        }
        var averageViewed =0
        if(hillforts.size>0) {
            averageViewed = totalViewed / hillforts.size
        }
        info("Average class viewed: $averageViewed")
        return averageViewed
    }

    //    The average number of hillforts the class still has to view
    override fun classAverageUnseen():Int{
        var totalUnseen = 0
        for (hillfort in hillforts)
        {
            if(!hillfort.visited) totalUnseen++
        }
        var averageUnseen = 0
        if (hillforts.size >0){
            totalUnseen/hillforts.size
        }
        info("Average class unseen: $averageUnseen")
        return averageUnseen
    }
}