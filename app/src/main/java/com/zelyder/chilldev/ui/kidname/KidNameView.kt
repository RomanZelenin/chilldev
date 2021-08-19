package com.zelyder.chilldev.ui.kidname

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.zelyder.chilldev.R
import com.zelyder.chilldev.extensions.dpToPx


class KidNameView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs),
    View.OnFocusChangeListener,
    View.OnClickListener {

    var checked: Boolean = false
        set(value) {
            field = value
//            val ivChecked = findViewById<ImageView>(R.id.ivChecked)
//            ivChecked.isVisible = true
//            updateBackgroundAndTextColor(isFocused, value)
        }

    var text: String = ""
        set(value) {
            field = value
            val tvName = findViewById<TextView>(R.id.tvName)
            tvName.text = value
        }

//    private var vForeground: View
    var icon: ImageView
    val root: View

    init {
        root =
        LayoutInflater.from(context).inflate(
            R.layout.kid_name_list_item, this, true
        )
//        inflate(context, R.layout.kid_name_list_item, this)
        icon = root.findViewById(R.id.list_item_icon)
//        vForeground = findViewById(R.id.vForeground)
//        ivAvatar = findViewById(R.id.ivAvatar)
//        context.obtainStyledAttributes(attrs, R.styleable.AccountView).apply {
//            try {
//                text = getString(R.styleable.AccountView_android_text).toString()
//                checked = getBoolean(R.styleable.AccountView_android_checked, false)
//            } finally {
//                recycle()
//            }
//        }
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
//        vForeground.isVisible = !hasFocus
//        if (hasFocus) {
//            icon.setBackgroundResource(R.drawable.imgborder)
//        } else {
//            icon.setBackgroundResource(0)
//        }

        if (checked) {
//            findViewById<ImageView>(R.id.ivChecked).isVisible = true
        }
    }
}