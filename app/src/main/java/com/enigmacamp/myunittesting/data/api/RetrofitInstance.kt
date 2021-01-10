package com.enigmacamp.myunittesting.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3003/unittest/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    val userRegistrationApi: UserRegistrationApi by lazy {
        retrofit.create(UserRegistrationApi::class.java)
    }

}