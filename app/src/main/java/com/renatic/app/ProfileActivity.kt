package com.renatic.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.renatic.app.api.ApiConfig
import com.renatic.app.databinding.ActivityProfileBinding
import com.renatic.app.manager.Toolbar2Manager
import com.renatic.app.response.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        val profile = getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val userName = profile.getString("name", "").toString()
        val userEmail = profile.getString("email", "").toString()
        setData(userName, userEmail)

        binding.btnLogout.setOnClickListener {
            getSharedPreferences("LoginSession", Context.MODE_PRIVATE).edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }

    private fun setData(name: String, email: String) {
        binding.tvProfileName.text = name
        binding.tvProfileEmail.text = email
    }
}