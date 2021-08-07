package com.zelyder.chilldev.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.zelyder.chilldev.R

class AppView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs),
    View.OnFocusChangeListener, View.OnClickListener {

    @ColorInt
    var color : Int =  ContextCompat.getColor(context, R.color.disabled)
    set(value) {
        field = value
        findViewById<AppCompatImageView>(R.id.app_icon).setColorFilter(value)
    }

    var text : String = ""
        set(value) {
            field = value
            findViewById<TextView>(R.id.app_text).text = value
        }

    private var checked : Boolean = false
    set(value) {
        field = value
        findViewById<Switch>(R.id.switch_view).isChecked = value
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        updateBackgroundAndTextColor(hasFocus)
    }

    override fun onClick(v: View?) {
        checked = !checked
    }

    init {
        inflate(context, R.layout.app_item_page, this)
        context.obtainStyledAttributes(attrs, R.styleable.AppView).apply {
            try {
                color = getColor(
                    R.styleable.AppView_android_color,
                    ContextCompat.getColor(context, R.color.disabled)
                )
                text = getString(R.styleable.AppView_android_text).toString()
            } finally {
                recycle()
            }
        }
        isClickable = true
        isFocusable = true

        setOnClickListener(this)

        findViewById<Switch>(R.id.switch_view).setOnCheckedChangeListener { buttonView, isChecked ->
            checked = isChecked
        }
    }

    private fun updateBackgroundAndTextColor(hasFocus: Boolean) {
        val (backgroundResource, textColorId) = if (hasFocus) {
            R.drawable.app_focused_background to R.color.dark
        } else {
            R.drawable.app_unfocused_background to R.color.white
        }
        val textColor = ContextCompat.getColor(context, textColorId)
        setBackgroundResource(backgroundResource)
        findViewById<TextView>(R.id.app_text).setTextColor(textColor)
    }
}