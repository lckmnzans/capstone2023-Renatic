package com.renatic.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patients(
    val name: String,
    val dob: String,
    val sex: Int
): Parcelable

data class PatientsData(
    val name: String,
    val bpjs: String,
    val tanggalLahir: String,
    val jkelamin: String,
    val beratbadan: String
)