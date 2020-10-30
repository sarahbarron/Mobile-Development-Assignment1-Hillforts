package org.wit.hillfort.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun findOne(user: UserModel): UserModel
    fun create(user: UserModel)
    fun delete(user: UserModel)
    fun authenticate(username: String, password: String)
    fun update(user: UserModel)
}