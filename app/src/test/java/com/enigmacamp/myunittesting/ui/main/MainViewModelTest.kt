package com.enigmacamp.myunittesting.ui.main

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockedStatic
import org.mockito.Mockito


class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
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
    fun userRegistration_onSuccess_returnResourceStateSuccess() {
        val dummyUser = UserRegistration("dummy", "1", "1", "test@example.com")
        val mainViewModel = MainViewModel()

        mainViewModel.userRegistration(dummyUser)

        val actualResult = mainViewModel.registrationStatusLiveData.value?.data as UserRegistration
        assertThat(actualResult.userName).isEqualTo(dummyUser.userName)
    }

    @Test
    fun userRegistration_onFailed_returnResourceStateFailed() {
        val dummyUser = UserRegistration("", "1", "1", "test@example.com")
        val mainViewModel = MainViewModel()

        mainViewModel.userRegistration(dummyUser)

        val actualResult = mainViewModel.registrationStatusLiveData.value?.message
        assertThat(actualResult).isEqualTo("Failed")
    }
}