package com.renatic.app.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatic.app.data.Patients
import com.renatic.app.data.PatientsAdapter
import com.renatic.app.databinding.ActivityMainBinding
import com.renatic.app.manager.ToolbarManager
import com.renatic.app.response.PatientItem
import com.renatic.app.viewModel.MainViewModel
import com.renatic.app.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: ToolbarManager
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = ToolbarManager(this)
        toolbar.setupToolbar()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, DataPatientActivity::class.java)
            intent.putExtra("ORIGIN", "FromMainActivity")
            startActivity(intent)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.svMain.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        binding.svMain.queryHint = "Cari"
        binding.svMain.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svMain.clearFocus()
                viewModel.searchPatient(this@MainActivity, query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.rvPatients.layoutManager = LinearLayoutManager(this)
        viewModel.getListOfPatient(this)
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.patientList.observe(this) { listOfPatients ->
            setPatientsData(listOfPatients)
        }
    }

    private fun setPatientsData(patients: List<PatientItem>?) {
        if (patients != null) {
            val list = ArrayList<Patients>()
            for (patient in patients) {
                val patientData = Patients(patient.namePatient, patient.noPatient, patient.tanggalLahir.slice(0..9), patient.kelamin.toString(), patient.weightPatient.toString())
                list.add(patientData)
            }

            val listPatients = PatientsAdapter(list)
            binding.rvPatients.adapter = listPatients

            listPatients.setOnItemClickListener(object: PatientsAdapter.OnItemClickListener {
                override fun onItemClicked(item: Patients) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIL, item)
                    startActivity(intent)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadMain.visibility = View.VISIBLE
        } else {
            binding.loadMain.visibility = View.GONE
        }
    }
}