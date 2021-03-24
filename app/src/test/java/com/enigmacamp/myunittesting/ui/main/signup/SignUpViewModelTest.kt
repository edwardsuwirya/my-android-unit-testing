package com.enigmacamp.myunittesting.ui.main.signup

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigmacamp.myunittesting.MainCoroutineRule
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doNothing
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*


class SignUpViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var logMock: MockedStatic<Log>

    @Mock
    var userRepository: UserRepository? = null
    lateinit var viewModel: SignUpViewModel

    @Before
    fun registerLogMock() {
        MockitoAnnotations.openMocks(this)
        logMock = Mockito.mockStatic(Log::class.java).apply {
            `when`<Log> { Log.d(any(), any()) }.thenAnswer { return@thenAnswer 1 }
        }

        viewModel =
            SignUpViewModel(userRepository!!, mainCoroutineRule.testDispatcherProvider)
    }

    @After
    fun unregisterLogMock() {
        logMock.close()
    }

    @Test
    fun userRegistration_onSuccess_returnResourceStateSuccess() {
        mainCoroutineRule.runBlockingTest {
            val dummyUser = UserRegistration(
                userName = "dummy",
                password = "1",
                confirmedPassword = "1",
                email = "test@example.com"
            )
            doNothing().`when`(userRepository!!).registerUser(any())

            viewModel.userRegistration(dummyUser)

            val actualResult =
                viewModel.registrationStatusLiveData.value?.data as Boolean
            assertThat(actualResult).isEqualTo(true)
        }

    }

    @Test
    fun userRegistration_onFailed_returnResourceStateFailed() {
        mainCoroutineRule.runBlockingTest {
            val dummyUser = UserRegistration(
                userName = "",
                password = "1",
                confirmedPassword = "1",
                email = "test@example.com"
            )
            doNothing().`when`(userRepository!!).registerUser(any())
            val mainViewModel =
                SignUpViewModel(userRepository!!, mainCoroutineRule.testDispatcherProvider)

            mainViewModel.userRegistration(dummyUser)

            val actualResult = mainViewModel.registrationStatusLiveData.value?.message
            assertThat(actualResult).isEqualTo("Failed")
        }
    }
}