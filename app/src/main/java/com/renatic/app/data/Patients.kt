package com.renatic.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patients(
    val name: String,
    val dob: String,
    val sex: String,
    val num: String? = null
): Parcelable

val dummyText: ArrayList<Patients> = arrayListOf(
    Patients("Jokowi", "12/12/2001", "Laki-laki", "0000123456789"),
    Patients("Widodo", "30/01/1995", "Laki-laki"),
)
