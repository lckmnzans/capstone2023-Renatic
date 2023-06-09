package com.renatic.app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.PatientsData
import com.renatic.app.databinding.ActivityFormPatientBinding
import com.renatic.app.manager.Toolbar2Manager
import com.renatic.app.response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormPatientBinding
    private lateinit var toolbar: Toolbar2Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        setSpinnerItem()

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val num = binding.etNum.text.toString()
            val dob = binding.etDob.getDate()
            val sex = arrayOf(1,2)[binding.spinnerSex.selectedItemPosition].toString()
            val weight = binding.etWeight.text.toString()
            submitData(name, num, dob, sex, weight)
        }
    }

    private fun setSpinnerItem() {
        val sexes = resources.getStringArray(R.array.sexes)
        binding.spinnerSex.apply {
            adapter = ArrayAdapter(
                this@FormPatientActivity,
                android.R.layout.simple_spinner_dropdown_item,
                sexes
            )
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    //Toast.makeText(this@FormPatientActivity, getString(R.string.selected_item) + " " + "" + sexes[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    //
                }
            }
        }
    }

    private fun submitData(name: String, num: String, dob: String, sex: String, weight: String) {
        // Hanya implementasi testing, bukan sebenarnya. API BELUM SELESAI
        /*
        val text = """
            $name, $num, $dob, $sex, $weight
        """.trimIndent()
        Log.d(TAG, text)*/

        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "")
        val request = PatientsData(name, num, dob, sex, weight)
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

    companion object {
        const val TAG = "FormPatientActivity"
    }
}