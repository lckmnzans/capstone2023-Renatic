package com.renatic.app.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.request.ScreeningRequest
import com.renatic.app.data.response.*
import com.renatic.app.databinding.ActivityResultBinding
import com.renatic.app.manager.Toolbar2Manager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var toolbar: Toolbar2Manager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        val id = intent.getStringExtra(EXTRA_ID).toString()
        getScreening(id)
    }

    private fun getScreening(id: String) {
        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token","")
        val client = ApiConfig.getApiService(token.toString()).getScreening(id)
        client.enqueue(object: Callback<ClinicalResponse>{
            override fun onResponse(
                call: Call<ClinicalResponse>,
                response: Response<ClinicalResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error.toBooleanStrict()) {
                        Log.d(TAG, "Respon didapat = " + responseBody.data[0].toString())
                        val data = responseBody.data[0]
                        val request = ScreeningRequest(
                            data.idSkrining.toString(),
                            data.pregnancies,
                            data.glucose,
                            data.blood,
                            data.skin,
                            data.insulin,
                            data.bmi,
                            data.diabetesDegree,
                            data.gambar
                        )
                        getScreeningTest(request)
                    }
                }
            }

            override fun onFailure(call: Call<ClinicalResponse>, t: Throwable) {
                Log.d(TAG, "${t.message}")
            }
        })
    }

    private fun getScreeningTest(request: ScreeningRequest) {
        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token","")
        lifecycleScope.launch(Dispatchers.IO) {
            val client = ApiConfig.getApiService(token.toString()).screeningTest(request)
            withContext(Dispatchers.Main) {
                val resultKlinis = client.data.formKlinis
                val resultRetina = client.data.imgKlinis
                setResult(resultKlinis, resultRetina)
                Toast.makeText(this@ResultActivity, "Hasil screening telah didapat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setResult(resultKlinis: FormKlinis, resultRetina: ImgKlinis) {
        binding.tvResult.text = "HASIL TELAH DIDAPAT"
        binding.tvR1.text = "Status terkena diabetes :".plus(resultKlinis.prediction)
        binding.tvR2.text = "Keakuratan hasil prediksi :".plus(resultKlinis.probabilty)
        if (resultRetina.retinaDetected) {
            binding.tvR3.text = "Level diabetic retinopathy :".plus(resultRetina.drClass)
        } else {
            binding.tvR3.text = "Diabetic retinopathy tidak terdeteksi"
        }
    }

    companion object {
        private const val TAG = "ResultActivity"
        const val EXTRA_ID = "extra_id"
    }
}