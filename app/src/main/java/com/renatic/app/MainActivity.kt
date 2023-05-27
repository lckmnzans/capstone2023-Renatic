package com.renatic.app

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.renatic.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toProfile: ImageView = findViewById(R.id.iv_to_profile)
        toProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, FormPatientActivity::class.java)
            startActivity(intent)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.svMain.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.svMain.queryHint = "Cari"
        binding.svMain.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svMain.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.view1.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}