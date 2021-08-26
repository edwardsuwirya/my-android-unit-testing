package com.enigmacamp.myunittesting.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.enigmacamp.myunittesting.data.dao.MyDatabase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.HTTP
import java.io.InputStreamReader
import java.net.HttpURLConnection

abstract class BaseTest {
    var myDatabase: MyDatabase? = null
    var webServerMock: MockWebServer? = null
    abstract fun isMockWebServerEnabled(): Boolean
    abstract fun isMockDatabaseEnabled(): Boolean

    @Before
    open fun setup() {
        this.configureMock()
    }

    @After
    open fun shutdown() {
        this.stopMock()
    }

    open fun configureMock() {
        if (isMockDatabaseEnabled()) {
            myDatabase =
                Room.inMemoryDatabaseBuilder(
                    InstrumentationRegistry.getInstrumentation().targetContext,
                    MyDatabase::class.java
                ).build()
        }
        if (isMockWebServerEnabled()) {
            webServerMock = MockWebServer()
        }
    }

    fun getJson(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        try {
            return reader.readText()
        } finally {
            reader.close()
        }
    }

    fun getRepsonse(path: String): MockResponse {
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

    open fun stopMock() {
        if (isMockDatabaseEnabled()) {
            myDatabase?.close()
        }
        if (isMockWebServerEnabled()) {
            webServerMock?.shutdown()
        }
    }
}