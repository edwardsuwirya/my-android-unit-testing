package com.enigmacamp.myunittesting

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.enigmacamp.myunittesting.data.dao.MyDatabase
import com.enigmacamp.myunittesting.data.model.UserRegistration
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class MyDatabaseTest {
    private lateinit var myDatabase: MyDatabase

    @Before
    fun initDb() {
        myDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            MyDatabase::class.java
        ).build()
    }

    fun createSampleData() {
        val dummyUser = UserRegistration(
            userName = "sample",
            password = "1",
            confirmedPassword = "1",
            email = "sample@example.com"
        )
        myDatabase.userDao().insert(dummyUser)
    }

    @After
    fun closeDb() {
        myDatabase.close()
    }

    @Test
    fun userDao_whenInsert_successFind() {
        val dummyUser = UserRegistration(
            userName = "dummy",
            password = "1",
            confirmedPassword = "1",
            email = "test@example.com"
        )
        myDatabase.userDao().insert(dummyUser)

        val actualResult = myDatabase.userDao().findUserByName(dummyUser.userName)
        assertThat(actualResult).isEqualTo(dummyUser)
    }

    @Test
    fun userDao_whenFindByName_returnUserRegistration() {
        val dummyUserName = "sample"
        createSampleData()
        val actualResult = myDatabase.userDao().findUserByName(dummyUserName)
        assertThat(actualResult.userName).isEqualTo(dummyUserName)
    }
}