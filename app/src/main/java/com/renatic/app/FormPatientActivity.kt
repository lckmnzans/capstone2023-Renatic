package com.renatic.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.renatic.app.databinding.ActivityFormPatientBinding
import com.renatic.app.manager.Toolbar2Manager

class FormPatientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormPatientBinding
    private lateinit var toolbar: Toolbar2Manager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = Toolbar2Manager(this)
        toolbar.setupToolbar()

        setSpinnerItem()

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val num = binding.etNum.text.toString()
            val dob = binding.etDob.getDate().toString()
            val sex = binding.spinnerSex.selectedItemPosition.toString()
            val weight = binding.etWeight.text.toString()
            submitData(name, num, dob, sex, weight)
        }
    }

    private fun setSpinnerItem() {
        val sexes = resources.getStringArray(R.array.sexes)
        binding.spinnerSex.apply {
            adapter = ArrayAdapter(this@FormPatientActivity, android.R.layout.simple_spinner_dropdown_item, sexes)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //Toast.makeText(this@FormPatientActivity, getString(R.string.selected_item) + " " + "" + sexes[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    //
                }
            }
        }
    }

    private fun submitData(name: String, num: String, dob: String, sex: String, weight: String) {
        // Hanya implementasi testing, bukan sebenarnya. Perlu ada API
        val text = """
            $name, $num, $dob, $sex, $weight
        """.trimIndent()
        Log.d(TAG, text)
    }

    companion object {
        const val TAG = "FormPatientActivity"
    }
}