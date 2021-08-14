package com.zelyder.chilldev.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zelyder.chilldev.R
import com.zelyder.chilldev.extensions.dpToPx
import de.hdodenhof.circleimageview.CircleImageView

class AccountView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs),
    View.OnFocusChangeListener,
    View.OnClickListener {

    var checked: Boolean = false
        set(value) {
            field = value
            val ivChecked = findViewById<ImageView>(R.id.ivChecked)
            ivChecked.isVisible = true
            updateBackgroundAndTextColor(isFocused, value)
        }

    var text: String = ""
        set(value) {
            field = value
            val tvName = findViewById<TextView>(R.id.tvName)
            tvName.text = value
        }

    private var vForeground: View
    private var ivAvatar: CircleImageView

    init {
        inflate(context, R.layout.account_item, this)
        vForeground = findViewById(R.id.vForeground)
        ivAvatar = findViewById(R.id.ivAvatar)
        context.obtainStyledAttributes(attrs, R.styleable.AccountView).apply {
            try {
                text = getString(R.styleable.AccountView_android_text).toString()
                checked = getBoolean(R.styleable.AccountView_android_checked, false)
            } finally {
                recycle()
            }
        }
        isClickable = true
        isFocusable = true

        setOnClickListener(this)
    }

    var customOnClick: (() -> Unit)? = null

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        updateBackgroundAndTextColor(hasFocus, checked)
    }

    override fun onClick(v: View?) {
        checked = !checked
        customOnClick?.invoke()
    }

    private fun updateBackgroundAndTextColor(hasFocus: Boolean, checked: Boolean) {
        vForeground.isVisible = !hasFocus
        ivAvatar.borderWidth = if (hasFocus) 4.dpToPx() else 0

        if (checked) {
            findViewById<ImageView>(R.id.ivChecked).isVisible = true
        }
    }
}