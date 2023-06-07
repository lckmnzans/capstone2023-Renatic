package com.renatic.app.customView

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class EditDate: AppCompatEditText {
    constructor(context: Context): super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    private val dateFormatter: DateTimeFormatter =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    private val dateFormat = "##/##/####"
    private val validDigits = "0123456789"
    private var isUpdating = false

    private fun init() {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                //
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isUpdating) {
                    return
                }

                val inputText = s.toString()
                val filteredText = filterInputText(inputText)
                val formattedText = formatText(filteredText)

                isUpdating = true
                setText(formattedText)
                setSelection(formattedText.length)
                isUpdating = false
            }
        })
    }

    private fun filterInputText(inputText: String): String {
        val filteredStringBuilder = StringBuilder()

        for (i in inputText.indices) {
            val currentChar = inputText[i]
            if (validDigits.contains(currentChar)) {
                filteredStringBuilder.append(currentChar)
            }
        }

        return filteredStringBuilder.toString()
    }

    private fun formatText(inputText: String): String {
        val formattedStringBuilder = StringBuilder()
        var inputIndex = 0

        for (i in dateFormat.indices) {
            val currentChar = dateFormat[i]
            if (currentChar == '#') {
                if (inputIndex < inputText.length) {
                    formattedStringBuilder.append(inputText[inputIndex])
                    inputIndex++
                } else {
                    break
                }
            } else {
                formattedStringBuilder.append(currentChar)
            }
        }

        return formattedStringBuilder.toString()
    }

    fun getDate(): LocalDate? {
        val text = text?.toString() ?: return null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return try {
                LocalDate.parse(text, dateFormatter)
            } catch (e: DateTimeParseException) {
                null
            }
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    fun setDate(date: LocalDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setText(date.format(dateFormatter))
        }
    }
}