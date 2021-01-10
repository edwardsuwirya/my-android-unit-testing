package com.enigmacamp.myunittesting

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.enigmacamp.myunittesting.data.dao.MyDatabase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection

abstract class BaseTest {
    var myDatabase: MyDatabase? = null
    var mockServer: MockWebServer? = null
    abstract fun isMockServerEnabled(): Boolean
    abstract fun isMockDatabaseEnabled(): Boolean

    @Before
    open fun setUp() {
        this.configureMock()
    }

    @After
    open fun tearDown() {
        this.stopMock()
    }

    open fun configureMock() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
        }
        if (isMockDatabaseEnabled()) {
            myDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                MyDatabase::class.java
            ).build()
        }
    }

    open fun stopMock() {
        if (isMockServerEnabled()) {
            mockServer?.shutdown()
        }
        if (isMockDatabaseEnabled()) {
            myDatabase?.close()
        }
    }

    private fun getJson(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        try {
            return reader.readText()
        } finally {
            reader.close()
        }
    }

    fun getResponse(path: String): MockResponse {
        return MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getJson(path))
    }

    fun getRetrofit(mockWebServer: MockWebServer): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}