package com.renatic.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.renatic.app.databinding.ActivityFormPatientBinding

class FormPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormPatientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)

        val btnBack: ImageView = findViewById(R.id.iv_back)
        btnBack.setOnClickListener {
            finish()
        }
    }
}