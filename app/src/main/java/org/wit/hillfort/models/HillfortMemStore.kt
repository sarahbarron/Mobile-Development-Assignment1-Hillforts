package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger

abstract class HillfortMemStore : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(placemark: HillfortModel) {
        hillforts.add(placemark)
        logAll()
    }

    fun logAll() {
        hillforts.forEach{ info("${it}") }
    }
}