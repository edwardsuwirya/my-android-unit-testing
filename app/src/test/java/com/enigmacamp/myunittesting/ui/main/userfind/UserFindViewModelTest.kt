package com.enigmacamp.myunittesting.ui.main.userfind

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigmacamp.myunittesting.MainCoroutineRule
import com.enigmacamp.myunittesting.data.dao.UserDao
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class UserFindViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    var userRepository: UserRepository? = null
    lateinit var logMock: MockedStatic<Log>

    lateinit var viewModel: UserFindViewModel

    @Before
    fun registerLogMock() {
        MockitoAnnotations.openMocks(this);
        logMock = Mockito.mockStatic(Log::class.java).apply {
            `when`<Log> { Log.d(any(), any()) }.thenAnswer { return@thenAnswer 1 }
        }
        viewModel = UserFindViewModel(userRepository!!, mainCoroutineRule.testDispatcherProvider)
    }

    @After
    fun unregisterLogMock() {
        logMock.close()
    }

    @Test
    fun findUserInfo_onUserExist_returnUser() {
        mainCoroutineRule.runBlockingTest {
            val dummyUser = UserRegistration(
                userId = "abc",
                userName = "dummy",
                password = "1",
                confirmedPassword = "1",
                email = "test@example.com"
            )
            Mockito.`when`(userRepository!!.getUserInfo(any())).thenReturn(dummyUser)
            viewModel.findUserInfo("dummy")

            val actualResult = viewModel.findUserStatueLiveData.value?.data as UserRegistration
            Truth.assertThat(actualResult.userId).isEqualTo("abc")
        }
    }

    @Test
    fun findUserInfo_onUserIsNotExist_returnMessage() {
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(userRepository!!.getUserInfo(any())).thenReturn(null)
            viewModel.findUserInfo("dummy")
            val actualResult = viewModel.findUserStatueLiveData.value

            val actualData = actualResult?.data
            Truth.assertThat(actualData).isNull()
        }
    }
}