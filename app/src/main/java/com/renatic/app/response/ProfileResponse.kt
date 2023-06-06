package com.renatic.app.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: List<ProfileItem?>,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class ProfileItem(

	@field:SerializedName("idUser")
	val idUser: Int,

	@field:SerializedName("nameUser")
	val nameUser: String,

	@field:SerializedName("password2")
	val password2: String,

	@field:SerializedName("password1")
	val password1: String,

	@field:SerializedName("email")
	val email: String
)
