package com.renatic.app.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bumptech.glide.Glide
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.request.ScreeningRequest
import com.renatic.app.data.response.*
import com.renatic.app.databinding.ActivityResultBinding
import com.renatic.app.helper.ModelWorker
import com.renatic.app.manager.Toolbar2Manager
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
        val age = intent.getIntExtra(EXTRA_AGE, 0)
        AGE = age
        getScreening(id)

    }

    private fun getScreening(id: String) {
        showLoading(true)
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
                            AGE.toDouble(),
                            data.gambar
                        )
                        val workerData = workDataOf(
                            "id" to request.id,
                            "pregnancies" to request.Pregnancies,
                            "glucose" to request.Glucose,
                            "bloodPressure" to request.BloodPressure,
                            "skinThickness" to request.SkinThickness,
                            "insulin" to request.Insulin,
                            "bmi" to request.BMI,
                            "diabetesPedigree" to request.DiabetesPedigreeFunction,
                            "age" to request.Age,
                            "img" to request.img,
                            "token" to token
                        )
                        val modelRequest = OneTimeWorkRequestBuilder<ModelWorker>()
                            .setInputData(workerData)
                            .build()
                        WorkManager.getInstance(this@ResultActivity).enqueue(modelRequest)
                        WorkManager.getInstance(this@ResultActivity).getWorkInfoByIdLiveData(modelRequest.id).observe(this@ResultActivity) {
                            if (it.state.isFinished) {
                                val dataS = it.outputData
                                showLoading(false)
                                setResult(
                                    prediction = dataS.getBoolean("prediction", false),
                                    probability = dataS.getString("probability").toString(),
                                    retinaDetected = dataS.getBoolean("retinaDetected", false),
                                    drClass = dataS.getString("drClass").toString(),
                                    imgUrl = request.img
                                )
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ClinicalResponse>, t: Throwable) {
                showLoading(false)
                Log.d(TAG, "${t.message}")
            }
        })
    }

    private fun setResult(prediction: Boolean, probability: String, retinaDetected: Boolean, drClass: String, imgUrl: String) {
        binding.tvResult.text = "HASIL TELAH DIDAPAT"
        binding.tvR1.text = "- ".plus(prediction)
        binding.tvR2.text = "- ".plus(probability)
        if (retinaDetected) {
            if (drClass == "No DR") {
                binding.tvR3.text = "- Diabetic Retinopathy tidak terdeteksi"
            } else {
                binding.tvR3.text = "- Level diabetic retinopathy :".plus(drClass)
            }
        } else {
            binding.tvR3.text = "- Gambar retina tidak terdeteksi"
        }
        Glide.with(binding.ivRetina.context).load(imgUrl).into(binding.ivRetina)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadImg.visibility = View.VISIBLE
        } else {
            binding.loadImg.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "ResultActivity"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AGE = "extra_age"
        var AGE = 0
    }
}