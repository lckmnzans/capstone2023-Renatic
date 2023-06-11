package com.renatic.app.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renatic.app.data.Patients
import com.renatic.app.databinding.ActivityDetailBinding
import com.renatic.app.manager.Toolbar2Manager

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        val detail = getParceableData()
        if (detail != null) {
            setPatientDetail(detail)
        }

        binding.btnToFormRetina.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormRetinaActivity::class.java)
            startActivity(intent)
        }

        binding.btnToFormClinical.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormClinicalActivity::class.java)
            startActivity(intent)
        }

        binding.ibEditProfile.setOnClickListener {
            val intent = Intent(this@DetailActivity, DataPatientActivity::class.java)
            intent.putExtra("ORIGIN", "FromDetailActivity")
            intent.putExtra(DataPatientActivity.EXTRA_DETAIL, detail)
            startActivity(intent)
        }
    }

    private fun setPatientDetail(detail: Patients) {
        binding.tvNameDetail.text = ": ".plus(detail.name)
        binding.tvDobDetail.text = ": ".plus(detail.dob)
        binding.tvSexDetail.text = ": ".plus(getSex(detail.sex.toInt()))
        binding.tvNumDetail.text = ": ".plus(detail.num)
    }

    @Suppress("DEPRECATION")
    private fun getParceableData(): Patients? {
        return if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, Patients::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DETAIL)
        }
    }

    /*
    private fun getAge(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val birthDate = LocalDate.parse(dateString, formatter)
        val currentDate = LocalDate.now()
        val age = Period.between(birthDate, currentDate).years
        return age.toString()
    }*/

    private fun getSex(sexId: Int): String {
        return when (sexId) {
            1 -> "Laki-laki"
            2 -> "Perempuan"
            else -> "None"
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }
}