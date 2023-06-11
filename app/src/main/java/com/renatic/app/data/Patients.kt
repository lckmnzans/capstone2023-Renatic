package com.renatic.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patients(
    val id: Int? = null,
    val name: String,
    val num: String,
    val dob: String,
    val sex: String,
    val weight: String
): Parcelable