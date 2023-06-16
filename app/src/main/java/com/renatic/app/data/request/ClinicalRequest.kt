package com.renatic.app.data.request

data class ClinicalRequest(
    val Pregnancies: Double,
    val Glucose: Double,
    val BloodPressure: Double,
    val SkinThickness: Double,
    val Insulin: Double,
    val BMI: Double,
    val DiabetesPedigreeFunction: Double
)
