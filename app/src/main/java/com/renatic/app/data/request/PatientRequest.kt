package com.renatic.app.data.request

data class PatientRequest(
    val name: String,
    val bpjs: String,
    val tanggalLahir: String,
    val jkelamin: String,
    val beratbadan: String
)