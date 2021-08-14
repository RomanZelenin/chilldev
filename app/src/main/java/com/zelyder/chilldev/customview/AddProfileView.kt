package com.zelyder.chilldev.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zelyder.chilldev.R

class AddProfileView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs),
    View.OnFocusChangeListener,
    View.OnClickListener {

    private var vForeground: View

    init {
        inflate(context, R.layout.add_profile_item, this)
        vForeground = findViewById(R.id.vForeground)
        isClickable = true
        isFocusable = true

        setOnClickListener(this)
        updateBackgroundAndTextColor(false)
    }

    var customOnClick: (() -> Unit)? = null

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        updateBackgroundAndTextColor(hasFocus)
    }

    override fun onClick(v: View?) {
        customOnClick?.invoke()
    }

    private fun updateBackgroundAndTextColor(hasFocus: Boolean) {
        vForeground.isVisible = !hasFocus
    }
}