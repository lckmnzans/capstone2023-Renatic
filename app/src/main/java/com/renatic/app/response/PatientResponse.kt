package com.renatic.app.response

import com.google.gson.annotations.SerializedName

data class PatientResponse(

	@field:SerializedName("data")
	val data: List<PatientItem>? = null,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class PatientItem(

	@field:SerializedName("kelamin")
	val kelamin: Int,

	@field:SerializedName("noPatient")
	val noPatient: String,

	@field:SerializedName("namePatient")
	val namePatient: String,

	@field:SerializedName("weightPatient")
	val weightPatient: Int,

	@field:SerializedName("idPatient")
	val idPatient: Int,

	@field:SerializedName("tanggalLahir")
	val tanggalLahir: String
)
