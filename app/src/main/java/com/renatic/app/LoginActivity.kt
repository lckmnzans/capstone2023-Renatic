package com.renatic.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.edtPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val password = binding.edtPassword.text
                if (password.length < 8) {
                    binding.edtPassword.error = "Password harus minimal 8 karakter"
                }
            }

            override fun afterTextChanged(t: Editable?) {
                //
            }
        } )

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
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)
        val client = ApiConfig.getApiService("").login(request)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error.toBooleanStrict()) {
                        val idVal = responseBody.data?.id.toString()
                        val tokenVal = responseBody.data?.token.toString()
                        val sessionManager = SessionManager(applicationContext)
                        sessionManager.saveSession(idVal, tokenVal)
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