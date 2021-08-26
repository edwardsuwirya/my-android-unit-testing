package com.enigmacamp.myunittesting.utils

import android.util.Log
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.MockedStatic
import org.mockito.Mockito

class RegistrationUtilTest {
    val userName = "Dummy"
    val password = "123"
    val confirmPassword = "123"
    val email = "dummy@example.com"

    lateinit var logMock: MockedStatic<Log>

    @Before
    fun registerLogMock() {
        logMock = Mockito.mockStatic(Log::class.java).apply {
            `when`<Log> {
                Log.d(any(), any())
            }.thenAnswer { return@thenAnswer 1 }
        }
    }

    @After
    fun unregisterLogMock() {
        logMock.close()
    }

    @Test
    fun registrationUtil_onAllFormFilled_returnTrue() {
        val actualResult =
            RegistrationUtil.validateRegistrationInput(userName, password, confirmPassword, email)

        assertThat(actualResult).isTrue()
    }

    @Test
    fun registrationUtil_onOneOfFieldIsEmpty_returnFalse() {
        val actualResult =
            RegistrationUtil.validateRegistrationInput("", password, confirmPassword, email)
        assertThat(actualResult).isFalse()
    }

    @Test
    fun registrationUtil_onEmailEmptyOrWrongPattern_returnFalse() {
        val actualWrongPatternResult =
            RegistrationUtil.validateRegistrationInput(userName, password, confirmPassword, "123")
        assertThat(actualWrongPatternResult).isFalse()

        val actualEmptyResult =
            RegistrationUtil.validateRegistrationInput(userName, password, confirmPassword, "")
        assertThat(actualEmptyResult).isFalse()
    }

    @Test
    fun registrationUtil_onPasswordNotMatch_returnFalse() {
        val actualPasswordNotMatch =
            RegistrationUtil.validateRegistrationInput(userName, "123", "234", email)
        assertThat(actualPasswordNotMatch).isFalse()
    }
}