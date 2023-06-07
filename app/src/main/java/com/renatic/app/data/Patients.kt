package com.renatic.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patients(
    val name: String,
    val age: Int,
    val sex: Int
): Parcelable

data class PatientsInfo(
    val name: String,
    val num: String,
    val dob: String,
    val sex: String,
    val weight: String
)