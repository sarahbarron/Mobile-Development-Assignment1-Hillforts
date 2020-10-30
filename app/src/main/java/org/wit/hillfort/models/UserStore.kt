package org.wit.hillfort.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun findOne(user: UserModel): UserModel
    fun create(user: UserModel)
    fun delete(user: UserModel)
    fun authenticate(user: UserModel): Boolean
    fun update(user: UserModel)
}