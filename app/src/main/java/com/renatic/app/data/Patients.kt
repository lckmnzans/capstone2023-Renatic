package com.renatic.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patients(
    val name: String,
    val age: Int,
    val sex: Int
): Parcelable
