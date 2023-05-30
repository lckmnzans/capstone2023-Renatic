package com.renatic.app.api

import com.renatic.app.response.LoginRequest
import com.renatic.app.response.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}