package com.enigmacamp.myunittesting.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "m_user", indices = arrayOf(Index("userName", unique = true)))
data class UserRegistration(
    @PrimaryKey
    val userId: String = "",
    val userName: String,
    val password: String,
    val confirmedPassword: String,
    val email: String
)