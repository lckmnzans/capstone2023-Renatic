package com.renatic.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.renatic.app.api.ApiConfig
import com.renatic.app.databinding.ActivityLoginBinding
import com.renatic.app.databinding.ActivityRegisterBinding
import com.renatic.app.response.RegisterRequest
import com.renatic.app.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.edtNama.text.toString()
            val email = binding.edtEmail.text.toString()
            val password1 = binding.edtPassword1.text.toString()
            val password2 = binding.edtPassword2.text.toString()

            if (name.isNullOrEmpty() || email.isNullOrEmpty() || password1.isNullOrEmpty() || password2.isNullOrEmpty()) {
                Toast.makeText(this, "Harap isi semuanya dengan benar", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(name, email, password1, password2)
            }
        }
    }

    private fun registerUser(name: String, email: String, password1: String, password2: String) {
        val request = RegisterRequest(name, email, password1, password2)
        val client = ApiConfig.getApiService().register(request)
        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error.toBooleanStrict()) {
                        Log.e(TAG, "onResponse : Register sukses")
                    } else {
                        Log.e(TAG, "onResponse: Register gagal")
                    }
                } else {
                    Log.e(TAG, "onResponse: Register gagal")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "RegisterActivity"
    }
}