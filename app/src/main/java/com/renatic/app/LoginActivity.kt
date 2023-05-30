package com.renatic.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.renatic.app.api.ApiConfig
import com.renatic.app.databinding.ActivityLoginBinding
import com.renatic.app.manager.SessionManager
import com.renatic.app.response.LoginRequest
import com.renatic.app.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isLoggedIn = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            toMain()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text
            val password = binding.edtPassword.text
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                loginUser(email.toString(), password.toString())
            } else {
                if (email.isNullOrEmpty()) {
                    binding.edtEmail.error = "Email harap diisi"
                }
                if (password.isNullOrEmpty()) {
                    binding.edtPassword.error = "Password harap diisi"
                }
            }
            /*
            * Implementasi jika input field email dan atau password kosong, maka tidak akan diproses
            * */
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)
        val client = ApiConfig.getApiService().login(request)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error.toBooleanStrict()) {
                        val sessionManager = SessionManager(applicationContext)
                        sessionManager.saveSession(responseBody.data?.token.toString())
                        Log.e(TAG, "onResponse : Login berhasil")

                        toMain()
                    } else {
                        Log.e(TAG, "onResponse : Login gagal")
                    }
                } else {
                    Log.e(TAG, "onResponse : Login gagal")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}