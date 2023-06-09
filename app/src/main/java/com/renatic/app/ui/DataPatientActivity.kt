package com.renatic.app.ui

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.renatic.app.R
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.Patients
import com.renatic.app.databinding.ActivityDataPatientBinding
import com.renatic.app.manager.Toolbar2Manager
import com.renatic.app.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataPatientBinding
    private lateinit var toolbar: Toolbar2Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        setData()
        val origin = getOrigin()
        val detail = getParcelableData()

        val temp = ArrayList<String>()
        if (detail != null && origin == "FromDetailActivity") {
            temp.addAll(listOf(detail.name, detail.num, detail.dob, detail.sex, detail.weight))
            binding.let {
                it.etName.setText(detail.name)
                it.etNum.setText(detail.num)
                it.etDob.setDate(detail.dob)
                it.spinnerSex.setSelection(detail.sex.toInt() - 1)
                it.etWeight.setText(detail.weight)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val num = binding.etNum.text.toString()
            val dob = binding.etDob.getDate()
            val sex = arrayOf(1,2)[binding.spinnerSex.selectedItemPosition].toString()
            val weight = binding.etWeight.text.toString()
            val data = listOf(name, num, dob, sex, weight)

            if (data.all { it.isNotBlank() }) {
                if (origin == "FromDetailActivity") {
                    if (temp.any { !data.contains(it) }) {
                        //updateData(data[0], data[1], data[2], data[3], data[4])
                        Toast.makeText(this, "Data telah dirubah", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Data tidak ada yang dirubah", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    //submitData(data[0], data[1], data[2], data[3], data[4])
                    Toast.makeText(this, "Data telah ditambahkan", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setData() {
        val sexes = resources.getStringArray(R.array.sexes)
        binding.spinnerSex.adapter = ArrayAdapter(this@DataPatientActivity, android.R.layout.simple_spinner_dropdown_item, sexes)
        binding.spinnerSex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //Toast.makeText(this@DataPatientActivity, getString(R.string.selected_item) + " " + "" + sexes[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //
            }
        }
    }

    private fun submitData(name: String, num: String, dob: String, sex: String, weight: String) {
        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "")
        val request = Patients(name, num, dob, sex, weight)
        val client = ApiConfig.getApiService(token.toString()).addPatient(request)
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    val error = responseBody.error.toBooleanStrict()
                    val message = responseBody.message
                    if (error) {
                        Log.d(TAG, message)
                    } else {
                        Log.d(TAG, message)
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun updateData(name: String, num: String, dob: String, sex: String, weight: String) {
        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "")
        val request = Patients(name, num, dob, sex, weight)
        val client = ApiConfig.getApiService(token.toString()).editPatient(request)
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    val error = responseBody.error.toBooleanStrict()
                    val message = responseBody.message
                    if (error) {
                        Log.d(TAG, message)
                    } else {
                        Log.d(TAG, message)
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    @Suppress("DEPRECATION")
    private fun getParcelableData(): Patients? {
        return if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, Patients::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DETAIL)
        }
    }

    private fun getOrigin(): String? {
        return intent.getStringExtra("ORIGIN")
    }

    companion object {
        const val TAG = "DataPatientActivity"
        const val EXTRA_DETAIL = "extra_detail"
    }
}