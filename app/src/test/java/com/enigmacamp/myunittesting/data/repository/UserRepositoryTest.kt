package com.enigmacamp.myunittesting.data.repository

import com.enigmacamp.myunittesting.data.api.UserRegistrationApi
import com.enigmacamp.myunittesting.data.api.UserRegistrationResponse
import com.enigmacamp.myunittesting.data.dao.UserDao
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

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
    fun userRepository_onSuccessRegistration_returnRegisteredUser() {
        runBlocking {
            `when`(userRegistrationApiMock.register(userDummy)).thenReturn(
                Response.success(200, UserRegistrationResponse("123"))
            )
            doNothing().`when`(userDaoMock).insert(userDummy)

            val userRepository = UserRepository(userDaoMock, userRegistrationApiMock)
            val actual = userRepository.registerUser(userDummy)
            assertThat(actual).isNotNull()
            assertThat(actual!!.userId).isEqualTo("123")
        }
    }

    @Test
    fun userRepository_onFailedApi_returnNull() {
        runBlocking {
            `when`(userRegistrationApiMock.register(userDummy)).thenReturn(
                Response.error(401, ResponseBody.create(null, "Error"))
            )
            doNothing().`when`(userDaoMock).insert(userDummy)
            val userRepository = UserRepository(userDaoMock, userRegistrationApiMock)
            val actual = userRepository.registerUser(userDummy)
            assertThat(actual).isNull()
        }
    }

    @Test(expected = Exception::class)
    fun userRepository_onFailedDb_returnNull() {
        runBlocking {
            `when`(userRegistrationApiMock.register(userDummy)).thenReturn(
                Response.success(200, UserRegistrationResponse("123"))
            )
            doThrow(Exception("Error")).`when`(userDaoMock).insert(userDummy)
            val userRepository = UserRepository(userDaoMock, userRegistrationApiMock)
            val actual = userRepository.registerUser(userDummy)
            assertThat(actual).isNull()
        }
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