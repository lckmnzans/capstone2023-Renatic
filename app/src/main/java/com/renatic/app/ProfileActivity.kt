package com.renatic.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.renatic.app.api.ApiConfig
import com.renatic.app.databinding.ActivityProfileBinding
import com.renatic.app.manager.Toolbar2Manager
import com.renatic.app.response.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        val id = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("id", "id")
        getProfileDetail(id.toString())

        binding.btnLogout.setOnClickListener {
            getSharedPreferences("LoginSession", Context.MODE_PRIVATE).edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }

    private fun getProfileDetail(id: String) {
        val token = getSharedPreferences("LoginSession", Context.MODE_PRIVATE).getString("token", "token")
        val client = ApiConfig.getApiService(token.toString()).getProfile(id)
        client.enqueue(object: Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.data.isNotEmpty() && !responseBody.error.toBooleanStrict()) {
                        val nameUser = responseBody.data[0]!!.nameUser
                        val emailUser = responseBody.data[0]!!.email
                        setData(nameUser, emailUser)
                        Log.e(LoginActivity.TAG, "onResponse : Profile sukses didapatkan")
                    } else {
                        Log.e(LoginActivity.TAG, "onResponse : Profile gagal didapatkan")
                    }
                } else {
                    Log.e(LoginActivity.TAG, "onResponse : Profile gagal didapatkan karena suatu hal")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setData(name: String, email: String) {
        binding.tvProfileName.text = name
        binding.tvProfileEmail.text = email
    }
}