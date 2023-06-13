package com.renatic.app.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("publicUrl")
	val publicUrl: String? = null,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)
