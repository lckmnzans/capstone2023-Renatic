package com.renatic.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class Renatic: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}