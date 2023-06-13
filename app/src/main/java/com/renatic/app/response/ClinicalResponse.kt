package com.renatic.app.response

import com.google.gson.annotations.SerializedName

data class ClinicalResponse(

	@field:SerializedName("data")
	val data: List<ClinicalItem?>,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class ClinicalItem(

	@field:SerializedName("pregnancies")
	val pregnancies: Int,

	@field:SerializedName("idKlinis")
	val idKlinis: Int,

	@field:SerializedName("glucose")
	val glucose: Int,

	@field:SerializedName("insulin")
	val insulin: Int,

	@field:SerializedName("patient")
	val patient: Int,

	@field:SerializedName("skin")
	val skin: Int,

	@field:SerializedName("diabetesDegree")
	val diabetesDegree: Int,

	@field:SerializedName("tanggalLahir")
	val tanggalLahir: String,

	@field:SerializedName("blood")
	val blood: Int,

	@field:SerializedName("bmi")
	val bmi: Int
)
