package com.enigmacamp.myunittesting.data.dao

import android.util.Log
import com.enigmacamp.myunittesting.data.model.UserRegistration
import java.util.UUID.randomUUID

class UserDao {
    fun createUser(userRegistration: UserRegistration): UserRegistration {
        val userUUID = randomUUID()
        val registeredUser = userRegistration.copy(userId = userUUID.toString())
        userList.add(registeredUser)
        return registeredUser
    }

    fun findUserByName(userName: String): UserRegistration? {
        val selectedUser = userList.filter {
            it.userName.equals(userName)
        }

        try {
            return selectedUser.first()
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    companion object {
        val userList = mutableListOf<UserRegistration>()
    }

}