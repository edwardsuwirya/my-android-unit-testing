package com.enigmacamp.myunittesting.utils

import android.util.Log
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito


class RegistrationUtilTest {
    lateinit var logMock: MockedStatic<Log>

    @Before
    fun registerLogMock() {
        logMock = Mockito.mockStatic(Log::class.java).apply {
            `when`<Log> { Log.d(any(), any()) }.thenAnswer { return@thenAnswer 1 }
        }
    }

    @After
    fun unregisterLogMock() {
        logMock.close()
    }

    @Test
    fun registrationUtil_onAllFullfill_returnTrue() {
        val actualEmptyResult =
            RegistrationUtil.validateRegistrationInput("dummy", "1", "1", "test@example.com")
        assertThat(actualEmptyResult).isTrue()
    }

    @Test
    fun registrationUtil_onEmptyUserName_returnFalse() {
        val actualResult =
            RegistrationUtil.validateRegistrationInput("", "1", "1", "test@example.com")
        assertThat(actualResult).isFalse()
    }

    @Test
    fun registrationUtil_onEmailOrWrongPattern_returnFalse() {
        val actualWrongPatternResult =
            RegistrationUtil.validateRegistrationInput("dummy", "1", "1", "test")
        assertThat(actualWrongPatternResult).isFalse()
        val actualEmptyResult =
            RegistrationUtil.validateRegistrationInput("dummy", "1", "1", "")
        assertThat(actualEmptyResult).isFalse()
    }

    @Test
    fun registrationUtil_onPasswordNotMatch_returnFalse() {
        val actualEmptyResult =
            RegistrationUtil.validateRegistrationInput("dummy", "1", "11", "test@example.com")
        assertThat(actualEmptyResult).isFalse()
    }

}