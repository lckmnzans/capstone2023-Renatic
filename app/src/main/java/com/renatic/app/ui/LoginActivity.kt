package com.renatic.app.ui

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import com.renatic.app.databinding.ActivityLoginBinding
import com.renatic.app.viewModel.LoginViewModel
import com.renatic.app.viewModel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        })

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text
            val password = binding.edtPassword.text
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                viewModel.loginUser(email.toString(), password.toString(), this)
                viewModel.isLoading.observe(this) { isLoading ->
                    showLoading(isLoading)
                }
                viewModel.isSuccess.observe(this) { isSuccess ->
                    if (isSuccess) toMain()
                }
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

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            ObjectAnimator.ofFloat(binding.loginLayout, View.ALPHA, 0.5f).start()
            binding.loadLogin.visibility = View.VISIBLE
        } else {
            ObjectAnimator.ofFloat(binding.loginLayout, View.ALPHA, 1f).start()
            binding.loadLogin.visibility = View.GONE
        }
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}