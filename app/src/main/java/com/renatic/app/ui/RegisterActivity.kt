package com.renatic.app.ui

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.renatic.app.databinding.ActivityRegisterBinding
import com.renatic.app.viewModel.RegisterViewModel
import com.renatic.app.viewModel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.edtNama.text.toString()
            val email = binding.edtEmail.text.toString()
            val password1 = binding.edtPassword1.text.toString()
            val password2 = binding.edtPassword2.text.toString()

            if (name.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, "Harap isi semuanya dengan benar", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.isLoading.observe(this) { isLoading ->
                    showLoading(isLoading)
                }
                viewModel.registerUser(name, email, password1, password2)
            }
        }
    }

    /* DEPRECATED
    private fun registerUser(name: String, email: String, password1: String, password2: String) {
        val request = RegisterRequest(name, email, password1, password2)
        val client = ApiConfig.getApiService("").register(request)
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
                        Log.e(TAG, "onResponse : Register gagal")
                    }
                } else {
                    Log.e(TAG, "onResponse: Register gagal")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }*/

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            ObjectAnimator.ofFloat(binding.registerLayout, View.ALPHA, 0.5f).start()
            binding.loadRegister.visibility = View.VISIBLE
        } else {
            ObjectAnimator.ofFloat(binding.registerLayout, View.ALPHA, 1f).start()
            binding.loadRegister.visibility = View.GONE
        }
    }
}