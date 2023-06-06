package com.renatic.app.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renatic.app.LoginActivity
import com.renatic.app.api.ApiConfig
import com.renatic.app.manager.SessionManager
import com.renatic.app.response.LoginRequest
import com.renatic.app.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(context: Context): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun loginUser(email: String, password: String, context: Context) {
        _isLoading.value = true
        val request = LoginRequest(email, password)
        val client = ApiConfig.getApiService("").login(request)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error.toBooleanStrict()) {
                        val idVal = responseBody.data?.id.toString()
                        val tokenVal = responseBody.data?.token.toString()
                        val sessionManager = SessionManager(context)
                        sessionManager.saveSession(idVal, tokenVal)
                        Log.e(TAG, "onResponse : Login berhasil")
                        _isSuccess.value = true
                    } else {
                        Log.d(TAG, "onResponse : Login gagal")
                    }
                } else {
                    Log.d(TAG, "onResponse : Login gagal")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}