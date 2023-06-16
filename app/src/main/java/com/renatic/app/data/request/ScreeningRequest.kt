package com.renatic.app.data.request

data class ScreeningRequest(
    val id: String,
    val Pregnancies: Int,
    val Glucose: Int,
    val BloodPressure: Int,
    val SkinThickness: Int,
    val Insulin: Int,
    val BMI: Int,
    val DiabetesPedigreeFunction: Int,
    val img: String
)
