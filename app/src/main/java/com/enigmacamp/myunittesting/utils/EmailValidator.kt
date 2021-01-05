package com.enigmacamp.myunittesting.utils

import java.util.regex.Pattern

object EmailValidator {
    val EMAIL_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidEmail(email: CharSequence?): Boolean {
        return !email.isNullOrEmpty() && EMAIL_PATTERN.matcher(email).matches()
    }

}