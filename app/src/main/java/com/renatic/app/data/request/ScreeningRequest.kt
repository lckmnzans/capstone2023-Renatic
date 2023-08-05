package com.renatic.app.data.request

data class ScreeningRequest(
    val id: String,
    val Pregnancies: Int,
    val Glucose: Double,
    val BloodPressure: Double,
    val SkinThickness: Double,
    val Insulin: Double,
    val BMI: Double,
    val DiabetesPedigreeFunction: Double,
    val Age: Double,
    val img: String
)
