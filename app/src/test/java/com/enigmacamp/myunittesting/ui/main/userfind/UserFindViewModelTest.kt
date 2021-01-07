package com.enigmacamp.myunittesting.ui.main.userfind

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigmacamp.myunittesting.data.dao.UserDao
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.google.common.truth.Truth
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


class UserFindViewModelTest {
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
    fun findUserInfo_onUserExist_returnUser() {
        val dummyUser = UserRegistration(
            userId = "abc",
            userName = "dummy",
            password = "1",
            confirmedPassword = "1",
            email = "test@example.com"
        )
        Mockito.`when`(userDao!!.findUserByName(any())).thenReturn(dummyUser)
        val viewModel = UserFindViewModel(UserRepository(userDao!!))
        viewModel.findUserInfo("dummy")

        val actualResult = viewModel.findUserStatueLiveData.value?.data as UserRegistration
        Truth.assertThat(actualResult.userId).isEqualTo("abc")
    }

    @Test
    fun findUserInfo_onUserIsNotExist_returnMessage() {
        Mockito.`when`(userDao!!.findUserByName(any())).thenReturn(null)
        val viewModel = UserFindViewModel(UserRepository(userDao!!))
        viewModel.findUserInfo("dummy")

        val actualResult = viewModel.findUserStatueLiveData.value

        val actualData = actualResult?.data
        Truth.assertThat(actualData).isNull()

        val actualMessage = actualResult?.message
        Truth.assertThat(actualMessage).isEqualTo("No Data Found")
    }
}