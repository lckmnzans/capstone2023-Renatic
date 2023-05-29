package com.renatic.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.renatic.app.databinding.ActivityFormClinicalBinding

class FormClinicalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormClinicalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormClinicalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)

        val btnBack = binding.toolbar2.ivBack
        btnBack.setOnClickListener {
            finish()
        }
    }
}