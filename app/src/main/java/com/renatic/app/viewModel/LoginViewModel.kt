package com.renatic.app.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.renatic.app.api.ApiConfig
import com.renatic.app.manager.SessionManager
import com.renatic.app.data.request.LoginRequest
import com.renatic.app.data.response.LoginResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
                        getUserProfile(idVal, tokenVal, context)
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
                Toast.makeText(context, "Login gagal", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun getUserProfile(id: String, token: String, context: Context) {
        runBlocking {
            launch {
                val response = ApiConfig.getApiService(token).getProfile(id)
                if (!response.error.toBooleanStrict()) {
                    val userProfile = response.data
                    saveData(userProfile[0]!!.nameUser, userProfile[0]!!.email, context)
                    Log.e(TAG, "onResponse : Profile sukses didapatkan")
                } else {
                    Log.e(TAG, "onResponse : Profile gagal didapatkan")
                }
            }
        }
    }

    private fun saveData(name: String, email: String, context: Context) {
        val sessionManager = SessionManager(context)
        sessionManager.saveProfile(name, email)
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}