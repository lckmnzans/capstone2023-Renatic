package com.renatic.app.data.response

import com.google.gson.annotations.SerializedName

data class ScreeningResponse(

	@field:SerializedName("data")
	val data: ResultData,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class FormKlinis(

	@field:SerializedName("probabilty")
	val probabilty: String,

	@field:SerializedName("advice")
	val advice: String,

	@field:SerializedName("prediction")
	val prediction: Boolean
)

data class ResultData(

	@field:SerializedName("BloodPressure")
	val bloodPressure: Int,

	@field:SerializedName("DiabetesPedigreeFunction")
	val diabetesPedigreeFunction: Int,

	@field:SerializedName("imgKlinis")
	val imgKlinis: ImgKlinis,

	@field:SerializedName("Glucose")
	val glucose: Int,

	@field:SerializedName("SkinThickness")
	val skinThickness: Int,

	@field:SerializedName("Insulin")
	val insulin: Int,

	@field:SerializedName("Pregnancies")
	val pregnancies: Int,

	@field:SerializedName("BMI")
	val bMI: Int,

	@field:SerializedName("formKlinis")
	val formKlinis: FormKlinis
)

data class ImgKlinis(

	@field:SerializedName("dr_class")
	val drClass: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("retina_detected")
	val retinaDetected: Boolean
)
