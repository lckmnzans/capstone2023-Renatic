package com.renatic.app.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renatic.app.api.ApiConfig
import com.renatic.app.response.ClinicalItem
import com.renatic.app.response.ClinicalResponse
import com.renatic.app.response.PatientItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(context: Context): ViewModel() {
    private val _clinicalData = MutableLiveData<List<ClinicalItem?>>()
    val clinicalData: LiveData<List<ClinicalItem?>> = _clinicalData

    private val _detailPatient = MutableLiveData<PatientItem>()
    val detailPatient: LiveData<PatientItem> = _detailPatient

    fun getDetailPatient(id: String, context: Context) {
        val token = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token","")
        runBlocking {
            launch {
                val response = ApiConfig.getApiService(token.toString()).getPatient(id)
                if (!response.error.toBooleanStrict() && response.data != null) {
                    _detailPatient.value = response.data[0]
                }
            }
        }
    }

    fun getClinicalData(id: String, context: Context) {
        val token = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token","")
        viewModelScope.launch {
            val client = ApiConfig.getApiService(token.toString()).getClinical(id)
            client.enqueue(object: Callback<ClinicalResponse> {
                override fun onResponse(
                    call: Call<ClinicalResponse>,
                    response: Response<ClinicalResponse>,
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if ((responseBody != null) && !responseBody.error.toBooleanStrict()) {
                            _clinicalData.value = responseBody.data
                        }
                        Log.d(TAG, "onResponse: ${responseBody!!.message}")
                    } else {
                        Log.d(TAG, "onResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ClinicalResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    fun getAge(dateString: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(dateString, formatter)
        val currentDate = LocalDate.now()
        return Period.between(birthDate, currentDate).years
    }

    fun getSex(sexId: Int): String {
        return when (sexId) {
            1 -> "Laki-laki"
            2 -> "Perempuan"
            else -> "None"
        }
    }

    companion object {
        const val TAG = "DetailActivity"
    }

}