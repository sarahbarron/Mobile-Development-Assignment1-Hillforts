package org.wit.hillfort.models

class HillfortMemStore : HillfortStore {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(placemark: HillfortModel) {
        hillforts.add(placemark)
    }
}