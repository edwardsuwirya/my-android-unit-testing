package com.enigmacamp.myunittesting

import android.util.Log

object RegistrationUtil {

    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword: String,
        email: String
    ): Boolean {
        if (userName.isNullOrEmpty() || password.isNullOrEmpty() || confirmedPassword.isNullOrEmpty() || email.isNullOrEmpty()) {
            Log.d("Validation", "Error required field")
            return false
        }
        if (!EmailValidator.isValidEmail(email)) {
            Log.d("Validation", "Error email pattern")
            return false
        }
        if (password != confirmedPassword) {
            Log.d("Validation", "Error password not match")
            return false
        }
        return true
    }
}