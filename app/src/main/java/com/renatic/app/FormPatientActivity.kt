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
import com.renatic.app.viewManager.Toolbar2Manager

class FormPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormPatientBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()
    }
}