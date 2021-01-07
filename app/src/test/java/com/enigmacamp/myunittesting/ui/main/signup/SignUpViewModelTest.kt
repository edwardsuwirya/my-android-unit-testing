package com.enigmacamp.myunittesting.ui.main.signup

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigmacamp.myunittesting.data.dao.UserDao
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class SignUpViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var logMock: MockedStatic<Log>

    @Mock
    var userDao: UserDao? = null

    @Before
    fun registerLogMock() {
        MockitoAnnotations.openMocks(this);
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
        val dummyUser = UserRegistration(
            userName = "dummy",
            password = "1",
            confirmedPassword = "1",
            email = "test@example.com"
        )
        Mockito.`when`(userDao!!.createUser(any())).thenReturn(dummyUser.copy(userId = "abc"))
        val mainViewModel = SignUpViewModel(userRepository = UserRepository(userDao!!))

        mainViewModel.userRegistration(dummyUser)

        val actualResult = mainViewModel.registrationStatusLiveData.value?.data as UserRegistration
        assertThat(actualResult.userId).isEqualTo("abc")
    }

    @Test
    fun userRegistration_onFailed_returnResourceStateFailed() {
        val dummyUser = UserRegistration(
            userName = "",
            password = "1",
            confirmedPassword = "1",
            email = "test@example.com"
        )
        Mockito.`when`(userDao!!.createUser(any())).thenReturn(dummyUser)
        val mainViewModel = SignUpViewModel(userRepository = UserRepository(userDao!!))

        mainViewModel.userRegistration(dummyUser)

        val actualResult = mainViewModel.registrationStatusLiveData.value?.message
        assertThat(actualResult).isEqualTo("Failed")
    }
}