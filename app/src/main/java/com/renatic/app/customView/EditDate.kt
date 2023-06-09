package com.renatic.app.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.renatic.app.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class EditDate: AppCompatEditText, View.OnTouchListener {
    constructor(context: Context): super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }
    private lateinit var clearButtonImage: Drawable

    private val dateFormatter: DateTimeFormatter =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy/MM/dd")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    private val dateFormat = "####-##-##"
    private val validDigits = "0123456789"
    private var isUpdating = false

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

    private fun showClearButton() {
        setButtonDrawables(endOfTheText = clearButtonImage)
    }

    private fun hideClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText:Drawable? = null,
        endOfTheText:Drawable? = null,
        bottomOfTheText: Drawable? = null
    ){
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun init() {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED

        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_clear) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                //
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
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

    fun getDate(): String {
        return text.toString()
    }

    fun setDate(date: String) {
        setText(date)
    }

    /*
    fun getDate(): LocalDate? {
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
    }*/
}