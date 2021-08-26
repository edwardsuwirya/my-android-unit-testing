package com.enigmacamp.myunittesting.data.api

import com.enigmacamp.myunittesting.data.BaseTest
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UserRegistrationApiTest : BaseTest() {
    override fun isMockWebServerEnabled() = true

    override fun isMockDatabaseEnabled() = false

    val userDummy = UserRegistration(
        userName = "dummy",
        password = "12345",
        confirmedPassword = "12345",
        email = "dummy@example.com"
    )

    @Test
    fun userRegistrationApi_onPostRegistration_returnResponse() {
        webServerMock?.let {
            runBlocking {
                val userRegistrationApi = getRetrofit(it).create(UserRegistrationApi::class.java)
                it.enqueue(getRepsonse("signup_whenSuccess.json"))
                val actualResponse = userRegistrationApi.register(userDummy)
                assertThat(actualResponse.isSuccessful)
                assertThat(actualResponse.body()).isNotNull()
                assertThat(actualResponse.body()!!.userId).isEqualTo("205e906a-6bf6-477a-bf4d-de3bb36c6a9c")
            }
        }
    }
}