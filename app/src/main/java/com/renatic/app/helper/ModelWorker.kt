package com.renatic.app.helper

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.request.ScreeningRequest
import java.lang.Exception

class ModelWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    private val screeningData = ScreeningRequest(
        id = inputData.getString("id").toString(),
        Pregnancies = inputData.getInt("pregnancies", 0),
        Glucose = inputData.getInt("glucose", 0),
        BloodPressure = inputData.getInt("bloodPressure", 0),
        SkinThickness = inputData.getInt("skinThickness", 0),
        Insulin = inputData.getInt("insulin", 0),
        BMI = inputData.getInt("bmi", 0),
        DiabetesPedigreeFunction = inputData.getInt("glucose", 0),
        img = inputData.getString("img").toString()
    )
    val token = inputData.getString("token")
    override suspend fun doWork(): Result {
        return try {
            val client = ApiConfig.getApiService(token.toString()).screeningTest(screeningData)
            val resultKlinis = client.data.formKlinis
            val resultRetina = client.data.imgKlinis
            val imgUrl = screeningData.img
            val outputData = workDataOf(
                "error" to client.error,
                "message" to client.message,
                "retinaDetected" to resultRetina.retinaDetected,
                "prediction" to resultKlinis.prediction,
                "probability" to resultKlinis.probabilty,
                "drClass" to resultRetina.drClass,
                "imgUrl" to imgUrl
            )
            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}