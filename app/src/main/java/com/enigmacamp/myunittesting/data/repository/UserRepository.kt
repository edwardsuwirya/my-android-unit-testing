package com.enigmacamp.myunittesting.data.repository

import com.enigmacamp.myunittesting.data.api.UserRegistrationApi
import com.enigmacamp.myunittesting.data.dao.UserDao
import com.enigmacamp.myunittesting.data.model.UserRegistration

class UserRepository constructor(
    private val userDao: UserDao,
    private val userRegistrationApi: UserRegistrationApi
) {
    suspend fun registerUser(user: UserRegistration): UserRegistration? {
        val registrationStatus = userRegistrationApi.register(user)
        if (registrationStatus.isSuccessful) {
            registrationStatus.body()?.let {
                val registeredUser = user.copy(userId = it.userId)
                try {
                    userDao.insert(registeredUser)
                    return registeredUser
                } catch (e: Exception) {
                    return null
                }
            } ?: run {
                return null
            }
        } else {
            return null
        }
    }

    fun getUserInfo(userName: String) = userDao.findUserByName(userName)
}