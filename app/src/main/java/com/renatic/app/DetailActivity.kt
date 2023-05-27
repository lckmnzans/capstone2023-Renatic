package com.renatic.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renatic.app.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToFormRetina.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormRetinaActivity::class.java)
            startActivity(intent)
        }

        binding.btnToFormClinical.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormClinicalActivity::class.java)
            startActivity(intent)
        }
    }
}