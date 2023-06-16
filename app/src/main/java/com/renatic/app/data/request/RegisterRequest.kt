package com.renatic.app.data.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password1: String,
    val password2: String
)
