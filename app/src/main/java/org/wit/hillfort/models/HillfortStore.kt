package org.wit.hillfort.models

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
    fun visited(hillfort: HillfortModel, boolean: Boolean)
    fun deleteImage(hillfort: HillfortModel, image: String)

}