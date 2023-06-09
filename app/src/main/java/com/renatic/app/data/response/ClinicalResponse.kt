package com.renatic.app.data.response

import com.google.gson.annotations.SerializedName

data class ClinicalResponse(

	@field:SerializedName("data")
	val data: List<ClinicalItem>,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class ClinicalItem(

	@field:SerializedName("pregnancies")
	val pregnancies: Int,

	@field:SerializedName("glucose")
	val glucose: Int,

	@field:SerializedName("probabilty")
	val probabilty: Any? = null,

	@field:SerializedName("idSkrining")
	val idSkrining: Int,

	@field:SerializedName("insulin")
	val insulin: Int,

	@field:SerializedName("advice")
	val advice: Any? = null,

	@field:SerializedName("skin")
	val skin: Int,

	@field:SerializedName("diabetesDegree")
	val diabetesDegree: Int,

	@field:SerializedName("retina_detected")
	val retinaDetected: Any? = null,

	@field:SerializedName("blood")
	val blood: Int,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("date_add")
	val dateAdd: Any? = null,

	@field:SerializedName("patient")
	val patient: Int,

	@field:SerializedName("prediction")
	val prediction: Any? = null,

	@field:SerializedName("class_name")
	val className: Any? = null,

	@field:SerializedName("bmi")
	val bmi: Int
)
