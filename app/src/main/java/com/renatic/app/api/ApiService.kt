package com.renatic.app.api

import com.renatic.app.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("showdatapatient/{id}")
    suspend fun getPatient(
        @Path("id") id: String
    ): PatientResponse

    @POST("search")
    fun searchPatient(
        @Body searchRequest: RequestBody
    ): Call<PatientResponse>


    @POST("addpatient")
    fun addPatient(
        @Body patientRequest: PatientRequest
    ): Call<RegisterResponse>

    @POST("editpatient/{id}")
    fun editPatient(
        @Path("id") id: Int,
        @Body patientRequest: PatientRequest
    ): Call<RegisterResponse>

    @POST("addklinis")
    fun addClinical(
        @Header("id") id: Int,
        @Body clinicalRequest: ClinicalRequest
    ): Call<RegisterResponse>

    @GET("dataKlinis/{id}")
    fun getClinical(
        @Path("id") id: String
    ): Call<ClinicalResponse>

    @Multipart
    @POST("upload")
    fun addImage(
        @Part file: MultipartBody.Part
    ): Call<UploadResponse>
}