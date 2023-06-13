package com.renatic.app.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.Patients
import com.renatic.app.databinding.ActivityDetailBinding
import com.renatic.app.manager.Toolbar2Manager
import com.renatic.app.response.ClinicalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        val detail = getParceableData()
        if (detail != null) {
            setPatientDetail(detail)
        }

        binding.btnToForm.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormClinicalActivity::class.java)
            intent.putExtra(FormClinicalActivity.EXTRA_DETAIL, detail)
            startActivity(intent)
        }

        binding.ibEditProfile.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormPatientActivity::class.java)
            intent.putExtra("ORIGIN", "FromDetailActivity")
            intent.putExtra(FormPatientActivity.EXTRA_DETAIL, detail)
            startActivity(intent)
        }
    }

    private fun setPatientDetail(detail: Patients) {
        binding.tvNameDetail.text = ": ".plus(detail.name)
        binding.tvDobDetail.text = ": ".plus(detail.dob)
        binding.tvSexDetail.text = ": ".plus(getSex(detail.sex.toInt()))
        binding.tvNumDetail.text = ": ".plus(detail.num)
    }

    @Suppress("DEPRECATION")
    private fun getParceableData(): Patients? {
        return if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, Patients::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DETAIL)
        }
    }

    private fun getClinicalData(id: String) {
        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token","")
        val client = ApiConfig.getApiService(token.toString()).getClinical(id)
        client.enqueue(object: Callback<ClinicalResponse>{
            override fun onResponse(
                call: Call<ClinicalResponse>,
                response: Response<ClinicalResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if ((responseBody != null) && !responseBody.error.toBooleanStrict()) {
                        //
                    }
                }
            }

            override fun onFailure(call: Call<ClinicalResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    /*
    private fun getAge(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(dateString, formatter)
        val currentDate = LocalDate.now()
        val age = Period.between(birthDate, currentDate).years
        return age.toString()
    }*/

    private fun getSex(sexId: Int): String {
        return when (sexId) {
            1 -> "Laki-laki"
            2 -> "Perempuan"
            else -> "None"
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}