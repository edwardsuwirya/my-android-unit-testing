package com.enigmacamp.myunittesting.data.repository

import com.enigmacamp.myunittesting.BaseTest
import com.enigmacamp.myunittesting.data.api.UserRegistrationApi
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Test


class UserRepositoryTest : BaseTest() {
    val dummyUser = UserRegistration(
        userName = "sample",
        password = "1",
        confirmedPassword = "1",
        email = "sample@example.com"
    )

    fun createSampleData() {
        myDatabase?.let {
            it.userDao().insert(dummyUser)
        }
    }

    @Test
    fun userRepository_onRegisterUserSuccess_returnUserRegistration() {
        runBlocking {
            mockServer?.let {
                val userRegistrationApi = getRetrofit(it).create(UserRegistrationApi::class.java)
                it.enqueue(getResponse("signup_whenSuccess.json"))

                myDatabase?.apply {
                    val userRepository = UserRepository(userDao(), userRegistrationApi)
                    userRepository.registerUser(dummyUser)

                    val actualResult = userDao().findUserByName(dummyUser.userName)
                    Truth.assertThat(actualResult.userId)
                        .isEqualTo("205e906a-6bf6-477a-bf4d-de3bb36c6a9c")
                }
            }
        }
    }

    @Test
    fun userRepository_onGetUserInfoSuccess_returnUserRegistration() {
        myDatabase?.apply {
            val dummyUserName = "sample"
            createSampleData()
            val actualResult = userDao().findUserByName(dummyUserName)
            Truth.assertThat(actualResult.userName).isEqualTo(dummyUserName)
        }
    }

    override fun isMockServerEnabled() = true

    override fun isMockDatabaseEnabled() = true
}
