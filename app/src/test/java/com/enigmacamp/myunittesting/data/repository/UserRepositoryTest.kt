package com.enigmacamp.myunittesting.data.repository

import com.enigmacamp.myunittesting.data.api.UserRegistrationApi
import com.enigmacamp.myunittesting.data.dao.UserDao
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserRepositoryTest {
    val userDummy = UserRegistration(
        userId = "123",
        userName = "Dummy",
        password = "", confirmedPassword = "", email = ""
    )

    @Mock
    lateinit var userDaoMock: UserDao

    @Mock
    lateinit var userRegistrationApiMock: UserRegistrationApi

    @Before
    fun registerMock() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun registerUser() {
    }

    @Test
    fun userRepository_onSuccessFindUserInfo_returnUserRegistration() {
        val userName = "Dummy"
        `when`(userDaoMock.findUserByName(userName)).thenReturn(userDummy)
        val userRepository = UserRepository(userDaoMock, userRegistrationApiMock)
        val actual = userRepository.getUserInfo(userName)
        assertThat(actual.userName).isEqualTo(userName)
    }
}