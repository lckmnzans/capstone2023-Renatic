package com.renatic.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renatic.app.databinding.ActivityScreeningBinding

class ScreeningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreeningBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}