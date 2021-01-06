package com.enigmacamp.myunittesting.data.repository

import com.enigmacamp.myunittesting.data.dao.UserDao
import com.enigmacamp.myunittesting.data.model.UserRegistration

class UserRepository constructor(private val userDao: UserDao) {
    fun registerUser(user: UserRegistration) = userDao.createUser(user)
    fun getUserInfo(userName: String) = userDao.findUserByName(userName)
}