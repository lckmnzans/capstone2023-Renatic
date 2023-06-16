package com.renatic.app.data.response

import com.google.gson.annotations.SerializedName

data class InputDataResponse(

	@field:SerializedName("data")
	val data: InputData? = null,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class InputData(

	@field:SerializedName("BloodPressure")
	val bloodPressure: Int,

	@field:SerializedName("DiabetesPedigreeFunction")
	val diabetesPedigreeFunction: Int,

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("Glucose")
	val glucose: Int,

	@field:SerializedName("SkinThickness")
	val skinThickness: Int,

	@field:SerializedName("Insulin")
	val insulin: Int,

	@field:SerializedName("Pregnancies")
	val pregnancies: Int,

	@field:SerializedName("BMI")
	val bMI: Int
)
