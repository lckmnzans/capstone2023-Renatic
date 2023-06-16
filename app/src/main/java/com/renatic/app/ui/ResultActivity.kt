package com.renatic.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renatic.app.databinding.ActivityResultBinding
import com.renatic.app.manager.Toolbar2Manager

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var toolbar: Toolbar2Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()
    }
}