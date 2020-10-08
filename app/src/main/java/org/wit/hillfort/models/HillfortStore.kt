package org.wit.hillfort.models

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun create(placemark: HillfortModel)
    fun info(s: String)
}