package com.enigmacamp.myunittesting.data.dao

import com.enigmacamp.myunittesting.data.BaseTest
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserDaoTest : BaseTest() {
    override fun isMockWebServerEnabled() = false

    override fun isMockDatabaseEnabled() = true

    val userDummy = UserRegistration(
        userName = "dummy",
        password = "12345",
        confirmedPassword = "12345",
        email = "dummy@example.com"
    )

    @Test
    fun userDao_onInsertUserRegistration() {
        myDatabase?.run {
            userDao().insert(userDummy)
            val actualResult = userDao().findUserByName(userDummy.userName)
            assertThat(actualResult).isNotNull()
            assertThat(actualResult.userName).isEqualTo(userDummy.userName)
        }
    }

    private fun createSampleData() {
        myDatabase?.let {
            it.userDao().insert(userDummy)
        }
    }

    @Test
    fun userDao_onFindUserByName_returnUserRegistration() {
        myDatabase?.run {
            createSampleData()
            val actualResult = userDao().findUserByName("dummy")
            assertThat(actualResult.userName).isEqualTo(" ")
        }
    }


}