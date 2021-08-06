package com.zelyder.chilldev.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zelyder.chilldev.R

class InterestView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs),
    View.OnFocusChangeListener, View.OnClickListener {

    var checked : Boolean = false
    set(value) {
        field = value
        findViewById<ImageView>(R.id.check_image_view).isVisible = value
        updateBackgroundAndTextColor(isFocused, value)
    }

    var text : String = ""
    set(value) {
        field = value
        findViewById<TextView>(R.id.interest_text).text = value
    }

    init {
        inflate(context, R.layout.interest_view, this)
        context.obtainStyledAttributes(attrs, R.styleable.InterestView).apply {
            try {
                text = getString(R.styleable.InterestView_android_text).toString()
                checked = getBoolean(R.styleable.InterestView_android_checked, false)
            } finally {
                recycle()
            }
        }
        isClickable = true
        isFocusable = true

        setOnClickListener(this)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        updateBackgroundAndTextColor(hasFocus, checked)
    }

    override fun onClick(v: View?) {
        checked = !checked
    }

    private fun updateBackgroundAndTextColor(hasFocus: Boolean, checked: Boolean) {
        val (backgroundResource, textColorId) = if (hasFocus) {
            R.drawable.interest_focused_background to R.color.dark
        } else {
            if (checked) {
                R.drawable.interest_checked_unfocused_background
            } else {
                R.drawable.interest_unchecked_unfocused_background
            } to R.color.white
        }
        val textColor = ContextCompat.getColor(context, textColorId)
        setBackgroundResource(backgroundResource)
        findViewById<TextView>(R.id.interest_text).setTextColor(textColor)
    }

}