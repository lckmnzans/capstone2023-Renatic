package com.renatic.app.manager

import android.content.Context

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)

    fun saveSession(id: String, token: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("id", id)
        editor.putString("token", token)
        editor.apply()
    }

    fun saveProfile(name: String, email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.apply()
    }
}