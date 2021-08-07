package com.zelyder.chilldev.ui.customkeyboard

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import com.zelyder.chilldev.R

class CustomNumKeyboard(
    context: Context?,
    attrs: AttributeSet?
) :
    LinearLayout(context, attrs) {
    var scale = resources.displayMetrics.density
    var textSize = 9 * scale
    var editText: EditText? = null
    var keyboardOutput: KeyboardOutput? = null
    var requiredLength: Int = - 1

    fun setupKeyboard(
        editText: EditText,
        requiredLength: Int = -1,
        keyboardOutput: KeyboardOutput
    ) {
        this.editText = editText
        this.keyboardOutput = keyboardOutput
        this.requiredLength = requiredLength
    }

    fun bindEditText(editText: EditText) {
        this.editText = editText
    }

    init {
        inflate(context, R.layout.num_keyboard_view, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findViewById<Button>(R.id.pin_btn_1).apply {
            setOnClickListener(ClickLister())
            requestFocus()
        }
        findViewById<Button>(R.id.pin_btn_2).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_3).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_4).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_5).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_6).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_7).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_8).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_9).setOnClickListener(ClickLister())
        findViewById<Button>(R.id.pin_btn_0).setOnClickListener(ClickLister())
        findViewById<ImageButton>(R.id.pin_btn_del).setOnClickListener(ClickLister())
    }

    inner class ClickLister : OnClickListener {
        override fun onClick(view: View?) {
            editText?.let {
                var len = it.length()
                when (view?.id) {
                    R.id.pin_btn_1 -> it.append("1")
                    R.id.pin_btn_2 -> it.append("2")
                    R.id.pin_btn_3 -> it.append("3")
                    R.id.pin_btn_4 -> it.append("4")
                    R.id.pin_btn_5 -> it.append("5")
                    R.id.pin_btn_6 -> it.append("6")
                    R.id.pin_btn_7 -> it.append("7")
                    R.id.pin_btn_8 -> it.append("8")
                    R.id.pin_btn_9 -> it.append("9")
                    R.id.pin_btn_0 -> it.append("0")
                    R.id.pin_btn_del -> {
                        if (len > 0) {
                            it.text?.delete(len - 1, len)
                        }
                        if (len == 1) {
                            keyboardOutput?.onFieldIsEmpty()
                        }
                    }
                }
                len = it.length()
                if (requiredLength != -1 && len == requiredLength) {
                    keyboardOutput?.onSizeIsReached()
                }
            }

        }

    }
}