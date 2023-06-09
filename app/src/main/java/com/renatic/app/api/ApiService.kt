package com.renatic.app.api

import com.renatic.app.data.PatientsData
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
    suspend fun getProfile(
        @Path("id") id: String
    ): ProfileResponse

    @GET("patient")
    suspend fun getAllPatient(): PatientResponse

    @POST("search")
    fun getPatient(
        @Body patientRequest: PatientRequest
    ): Call<PatientResponse>

    @POST("addpatient")
    fun addPatient(
        @Body patientRequest: PatientsData
    ): Call<RegisterResponse>
}