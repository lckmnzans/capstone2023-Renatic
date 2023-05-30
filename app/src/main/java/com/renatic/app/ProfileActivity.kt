package com.renatic.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.renatic.app.databinding.ActivityProfileBinding
import com.renatic.app.viewManager.Toolbar2Manager
import kotlin.system.exitProcess

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        binding.btnLogout.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }
}