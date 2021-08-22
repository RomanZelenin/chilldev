package com.zelyder.chilldev.ui.kidname

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.zelyder.chilldev.R


class KidNameView(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs),
    View.OnClickListener {

    var icon: ImageView
    private val root: View = LayoutInflater.from(context).inflate(
        R.layout.kid_name_list_item, this, true
    )

    init {
        icon = root.findViewById(R.id.list_item_icon)
        isClickable = true
        isFocusable = true

        setOnClickListener(this)
    }

    var customOnClick: (() -> Unit)? = null

    override fun onClick(v: View?) {
        customOnClick?.invoke()
    }
}