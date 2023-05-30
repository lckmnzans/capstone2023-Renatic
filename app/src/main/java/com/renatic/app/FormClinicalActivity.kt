package com.renatic.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.renatic.app.databinding.ActivityFormClinicalBinding
import com.renatic.app.viewManager.Toolbar2Manager

class FormClinicalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormClinicalBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormClinicalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()
    }
}