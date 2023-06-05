package com.renatic.app

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.Patients
import com.renatic.app.data.PatientsAdapter
import com.renatic.app.data.dummyText
import com.renatic.app.databinding.ActivityMainBinding
import com.renatic.app.manager.SessionManager
import com.renatic.app.manager.ToolbarManager
import com.renatic.app.response.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: ToolbarManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = ToolbarManager(this)
        toolbar.setupToolbar()

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
                // Operasi querying ke server
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            // Lakukan operasi untuk memperbarui data RecyclerView di sini

            // Setelah selesai, beritahu SwipeRefreshLayout bahwa pembaruan selesai
            binding.swipeRefreshLayout.isRefreshing = false
        }

        val id = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("id", "").toString()
        getUserProfile(id)


        binding.rvPatients.layoutManager = LinearLayoutManager(this)
        setPatientsData(dummyText)
    }

    private fun setPatientsData(patients: ArrayList<Patients>) {
        val list = ArrayList<Patients>()
        for (patient in patients) {
            val patientData = Patients(patient.name, patient.dob, patient.sex, patient.num)
            list.add(patientData)
        }

        val listPatients = PatientsAdapter(patients)
        binding.rvPatients.adapter = listPatients

        listPatients.setOnItemClickListener(object: PatientsAdapter.OnItemClickListener {
            override fun onItemClicked(item: Patients) {
                val patientDetail = Patients(item.name, item.dob, item.sex, item.num)
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, patientDetail)
                startActivity(intent)
            }
        })
    }

    private fun getUserProfile(id: String) {
        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "token")
        lifecycleScope.launch(Dispatchers.Default) {
            val client = ApiConfig.getApiService(token.toString()).getProfile(id)
            client.enqueue(object: Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()!!
                        if (responseBody.data.isNotEmpty() && !responseBody.error.toBooleanStrict()) {
                            val name = responseBody.data[0]!!.nameUser
                            val email = responseBody.data[0]!!.email
                            saveData(name, email)
                            Log.e(LoginActivity.TAG, "onResponse : Profile sukses didapatkan")
                        } else {
                            Log.e(LoginActivity.TAG, "onResponse : Profile gagal didapatkan")
                        }
                    } else {
                        Log.e(LoginActivity.TAG, "onResponse : Profile gagal didapatkan karena suatu hal")
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun saveData(name: String, email: String) {
        val sessionManager = SessionManager(applicationContext)
        sessionManager.saveProfile(name, email)
    }
}