package com.enigmacamp.myunittesting.data.repository

import com.enigmacamp.myunittesting.data.BaseTest
import com.enigmacamp.myunittesting.data.api.UserRegistrationApi
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UserRepositoryFullTest : BaseTest() {
    override fun isMockWebServerEnabled() = true

    override fun isMockDatabaseEnabled() = true
    val userDummy = UserRegistration(
        userName = "dummy",
        password = "12345",
        confirmedPassword = "12345",
        email = "dummy@example.com"
    )

    @Test
    fun userRepository_onSuccessRegisterUser_returnUserRegistration() {
        runBlocking {
            webServerMock?.let {
                val userRegistrationApi = getRetrofit(it).create(UserRegistrationApi::class.java)
                it.enqueue(getRepsonse("signup_whenSuccess.json"))

                myDatabase?.apply {
                    val userRepository = UserRepository(userDao(), userRegistrationApi)
                    userRepository.registerUser(userDummy)

                    val actualResult = userDao().findUserByName(userDummy.userName)
                    assertThat(actualResult.userId).isEqualTo("205e906a-6bf6-477a-bf4d-de3bb36c6a9c")
                }
            }
        }
    }
}