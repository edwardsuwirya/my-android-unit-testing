package com.enigmacamp.myunittesting.data.api

import com.enigmacamp.myunittesting.data.model.UserRegistration
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRegistrationApi {
    @POST("/registration")
    suspend fun register(@Body userRegistration: UserRegistration): Response<UserRegistrationResponse>
}