package com.zelyder.chilldev.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import com.zelyder.chilldev.R

class ScrollBarView(context: Context, attrSet: AttributeSet?) :
    LinearLayoutCompat(context, attrSet) {

    private var defaultSelectedPosition = 1
    private var _selectedPosition = defaultSelectedPosition
    private val imageViewUp = lazy {
        ImageView(context).apply {
            setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_baseline_keyboard_arrow_up_24
                )
            )
            visibility = View.INVISIBLE
        }
    }
    private val imageViewDown = lazy {
        ImageView(context).apply {
            setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_baseline_keyboard_arrow_down_24
                )
            )
            visibility = View.INVISIBLE
        }
    }

    init {
        context.obtainStyledAttributes(attrSet, R.styleable.ScrollBarView).apply {
            try {
                numItems = getInteger(R.styleable.ScrollBarView_numItems, 0)
            } finally {
                recycle()
            }
        }
    }

    var selectedPosition: Int
        get() {
            return _selectedPosition
        }
        set(value) {
            if (size - 2 == 0) return
            _selectedPosition = value
            val startPos = 1
            val endPos = size - 2

            for (i in startPos..endPos) {
                (this[i] as TextView).apply {
                    background =
                        AppCompatResources.getDrawable(context, R.drawable.circle_item)
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                    text = ""
                }
            }
            if (startPos != endPos) {
                when (value + 1) {
                    endPos -> {
                        this[0].visibility = View.VISIBLE
                        this[endPos + 1].visibility = View.INVISIBLE
                    }
                    startPos -> {
                        this[0].visibility = View.INVISIBLE
                        this[endPos + 1].visibility = View.VISIBLE
                    }
                    else -> {
                        this[0].visibility = View.VISIBLE
                        this[endPos + 1].visibility = View.VISIBLE
                    }
                }
            }
            val item = this[value + 1] as TextView
            item.apply {
                background =
                    AppCompatResources.getDrawable(context, R.drawable.circle_item_selected)
                text = (value + 1).toString()
            }
        }


    var numItems: Int
        get() = if (size == 0) size else size - 2
        set(value) {
            removeAllViews()
            if (value > 0) {
                addView(imageViewUp.value)
                for (i in 0 until value) {
                    val item = TextView(context).apply {
                        background =
                            AppCompatResources.getDrawable(context, R.drawable.circle_item)
                        setTextColor(ContextCompat.getColor(context, android.R.color.black))
                        gravity = Gravity.CENTER
                    }
                    addView(item)
                }
                selectedPosition = defaultSelectedPosition
                addView(imageViewDown.value)
            }
        }


    fun setOnScrollListenerToPreviousItem(listener: OnClickListener) {
        imageViewUp.value.setOnClickListener(listener)
    }

    fun setOnScrollListenerToNextItem(listener: OnClickListener) {
        imageViewDown.value.setOnClickListener(listener)
    }


}