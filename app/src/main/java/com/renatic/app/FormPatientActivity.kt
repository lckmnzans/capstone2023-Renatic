package com.renatic.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.etDob.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s.toString()
                if (inputText.isNotEmpty()) {
                    if (inputText.length == 2 && before == 0) {
                        binding.etDob.setText("$inputText/")
                        binding.etDob.setSelection(binding.etDob.text.length)
                    } else if (inputText.length == 2 && before == 1 && inputText[1] != '/') {
                        binding.etDob.setText(inputText.substring(0, 1))
                        binding.etDob.setSelection(binding.etDob.text.length)
                    } else if (inputText.length == 5 && before == 0) {
                        binding.etDob.setText("$inputText/")
                        binding.etDob.setSelection(binding.etDob.text.length)
                    } else if (inputText.length == 5 && before == 1 && inputText[4] != '/') {
                        binding.etDob.setText(inputText.substring(0, 4))
                        binding.etDob.setSelection(binding.etDob.text.length)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                //
            }
        })
    }

    private fun stringToDate() {

    }
}