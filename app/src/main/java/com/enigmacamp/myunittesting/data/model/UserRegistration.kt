package com.enigmacamp.myunittesting.data.model

data class UserRegistration(
    val userId: String = "",
    val userName: String,
    val password: String,
    val confirmedPassword: String,
    val email: String
)