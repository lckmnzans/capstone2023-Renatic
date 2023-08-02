package com.renatic.app.viewModel

import android.content.Context
import android.util.Log
import androidx.collection.LruCache
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renatic.app.api.ApiConfig
import com.renatic.app.helper.Stack
import com.renatic.app.data.response.PatientItem
import com.renatic.app.data.response.PatientResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(context: Context): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _patientList = MutableLiveData<List<PatientItem>?>()
    val patientList: LiveData<List<PatientItem>?> = _patientList

    val listPatientStack = Stack<List<PatientItem>>()

    private val _internetAvailable = MutableLiveData<Boolean>()
    val internetAvailable: LiveData<Boolean> = _internetAvailable

    suspend fun getListOfPatient(context: Context) {
        _isLoading.value = true
        val token = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "")
        val response = ApiConfig.getApiService(token.toString()).getAllPatient()
        val listOfPatients = response.data
        if (listOfPatients != null) {
            _isLoading.value = false
            _patientList.value = listOfPatients
            listPatientStack.push(listOfPatients)
        }
    }

    fun searchPatient(context: Context, bpjs: String) {
        _isLoading.value = true
        val token = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token","")
        val request = "{\"bpjs\":\"$bpjs\"}".toRequestBody("application/json".toMediaType())
        val call = ApiConfig.getApiService(token.toString()).searchPatient(request)
        call.enqueue(object: Callback<PatientResponse> {
            override fun onResponse(
                call: Call<PatientResponse>,
                response: Response<PatientResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error.toBooleanStrict()) {
                        val listOfPatients = responseBody.data
                        if (listOfPatients != null) {
                            listPatientStack.push(listOfPatients)
                            _patientList.value = listOfPatients
                        }
                        Log.d(TAG, "onResponse: Pasien berhasil ditemukan")
                    } else {
                        Log.d(TAG, "onResponse: Pasien tidak ditemukan")
                    }
                } else {
                    Log.d(TAG, "onResponse: Pasien tidak ditemukan")
                }
            }

            override fun onFailure(call: Call<PatientResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: Pasien tidak ditemukan")
            }
        })
    }

    companion object {
        const val TAG = "MainActivity"
    }
}