package com.renatic.app.data.response

import com.google.gson.annotations.SerializedName

data class ListScreeningResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class DataItem(

	@field:SerializedName("pregnancies")
	val pregnancies: Int,

	@field:SerializedName("glucose")
	val glucose: Int,

	@field:SerializedName("probabilty")
	val probabilty: Any,

	@field:SerializedName("idSkrining")
	val idSkrining: Int,

	@field:SerializedName("insulin")
	val insulin: Int,

	@field:SerializedName("advice")
	val advice: Any,

	@field:SerializedName("skin")
	val skin: Int,

	@field:SerializedName("diabetesDegree")
	val diabetesDegree: Int,

	@field:SerializedName("retina_detected")
	val retinaDetected: Any,

	@field:SerializedName("blood")
	val blood: Int,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("date_add")
	val dateAdd: Any,

	@field:SerializedName("patient")
	val patient: Int,

	@field:SerializedName("prediction")
	val prediction: Any,

	@field:SerializedName("class_name")
	val className: Any,

	@field:SerializedName("bmi")
	val bmi: Int
)
