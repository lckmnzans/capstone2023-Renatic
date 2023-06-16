package com.renatic.app.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renatic.app.api.ApiConfig
import com.renatic.app.data.request.RegisterRequest
import com.renatic.app.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(context: Context): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(name: String, email: String, password1: String, password2: String) {
        _isLoading.value = true
        val request = RegisterRequest(name, email, password1, password2)
        val client = ApiConfig.getApiService("").register(request)
        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error.toBooleanStrict()) {
                        Log.d(TAG, "onResponse : Register sukses")
                    } else {
                        Log.d(TAG, "onResponse : Register gagal")
                    }
                } else {
                    Log.d(TAG, "onResponse: Register gagal")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "RegisterActivity"
    }
}