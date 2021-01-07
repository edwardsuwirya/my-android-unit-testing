package com.enigmacamp.myunittesting.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.enigmacamp.myunittesting.data.model.UserRegistration

@Dao
interface UserDao : BaseDao<UserRegistration> {
    @Query("SELECT * FROM m_user WHERE userName=:userName")
    fun findUserByName(userName: String): UserRegistration
}