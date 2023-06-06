package com.renatic.app.api

import com.renatic.app.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("profile/{id}")
    fun getProfile(
        @Path("id") id: String
    ): Call<ProfileResponse>

    @GET("patient")
    suspend fun getAllPatient(): PatientResponse
}