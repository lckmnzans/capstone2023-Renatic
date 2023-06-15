package com.renatic.app.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.renatic.app.data.ClinicalAdapter
import com.renatic.app.data.Patients
import com.renatic.app.databinding.ActivityDetailBinding
import com.renatic.app.manager.Toolbar2Manager
import com.renatic.app.response.ClinicalItem
import com.renatic.app.response.PatientItem
import com.renatic.app.toPatients
import com.renatic.app.viewModel.DetailViewModel
import com.renatic.app.viewModel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var toolbar: Toolbar2Manager
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()


        val detail = getParceableData()
        if (detail != null) {
            ID = detail.id.toString()
            setPatientDetail(detail)
        }

        viewModel.getDetailPatient(ID, this)
        viewModel.detailPatient.observe(this) {
            setPatientDetail(it.toPatients())
        }

        binding.rvHistoris.layoutManager = LinearLayoutManager(this)
        viewModel.getClinicalData(ID, this)
        viewModel.clinicalData.observe(this) {
            setPatientClinical(it)
        }

        binding.btnToForm.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormClinicalActivity::class.java)
            intent.putExtra(FormClinicalActivity.EXTRA_DETAIL, detail)
            startActivity(intent)
        }

        binding.ibEditProfile.setOnClickListener {
            val intent = Intent(this@DetailActivity, FormPatientActivity::class.java)
            intent.putExtra("ORIGIN", "FromDetailActivity")
            intent.putExtra(FormPatientActivity.EXTRA_DETAIL, detail)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDetailPatient(ID, this)
    }

    private fun setPatientDetail(detail: Patients) {
        binding.tvNameDetail.text = ": ".plus(detail.name)
        binding.tvDobDetail.text = ": ".plus(detail.dob)
        binding.tvSexDetail.text = ": ".plus(viewModel.getSex(detail.sex.toInt()))
        binding.tvNumDetail.text = ": ".plus(detail.num)
    }

    private fun setPatientClinical(listClinical: List<ClinicalItem?>) {
        if (listClinical.isNotEmpty()) {
            val list = ArrayList<ClinicalItem>()
            for (clinicalItem in listClinical) {
                if (clinicalItem != null) {
                    val item = ClinicalItem(
                        pregnancies = clinicalItem.pregnancies,
                        idKlinis = clinicalItem.idKlinis,
                        glucose = clinicalItem.glucose,
                        insulin = clinicalItem.insulin,
                        patient = clinicalItem.patient,
                        skin = clinicalItem.skin,
                        diabetesDegree = clinicalItem.diabetesDegree,
                        tanggalLahir = clinicalItem.tanggalLahir,
                        blood = clinicalItem.blood,
                        bmi = clinicalItem.bmi
                    )
                    list.add(item)
                }
            }

            val listClinical = ClinicalAdapter(list)
            binding.rvHistoris.adapter = listClinical
        }
    }

    @Suppress("DEPRECATION")
    private fun getParceableData(): Patients? {
        return if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DETAIL, Patients::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DETAIL)
        }
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        private var ID = "0"
    }
}