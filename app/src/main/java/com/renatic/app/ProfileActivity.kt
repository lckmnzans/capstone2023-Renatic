package com.renatic.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.renatic.app.databinding.ActivityProfileBinding
import kotlin.system.exitProcess

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)

        val btnBack = binding.toolbar2.ivBack
        btnBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }
}