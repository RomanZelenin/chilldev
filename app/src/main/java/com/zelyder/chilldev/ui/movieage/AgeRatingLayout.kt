package com.zelyder.chilldev.ui.movieage

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.zelyder.chilldev.R

class AgeRatingLayout(context: Context, attrSet: AttributeSet?) :
    LinearLayout(context, attrSet) {

    private val size = AgeRating.values().size

    var selectedPosition = 0
        set(value) {
            field = value
            val startPosition = 0
            val endPosition = size - 1
            for (i in startPosition..selectedPosition) {
                (this[i] as TextView).apply {
                    configureFocusedTextView(this)
                }
            }
            for (i in selectedPosition + 1..endPosition) {
                (this[i] as TextView).apply {
                    configureDefaultTextView(this, isLast(i))
                }
            }
            (this[value] as TextView).apply {
               configureSelectedTextView(this, isLast(value))
            }
        }

    init {
        removeAllViews()
        for (i in 0 until size) {
            val tvAgeRating = TextView(context).apply {
                configureDefaultTextView(this, isLast(i))
                gravity = Gravity.CENTER
                textSize = 14.dpToPx().toFloat()
                text = AgeRating.values()[i].rating
            }
            addView(tvAgeRating)
        }
        selectedPosition = 0
    }

    private fun isLast(position: Int) = position == size - 1

    private fun configureFocusedTextView(textView: TextView): TextView {
        return textView.apply {
            setBackgroundResource(R.drawable.shp_focused_age_rating_bg)
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
            val layoutParams = LayoutParams(60.dpToPx(), 60.dpToPx())
            layoutParams.rightMargin = 8.dpToPx()
            setLayoutParams(layoutParams)
        }
    }

    private fun configureDefaultTextView(textView: TextView, isLast: Boolean): TextView {
        return textView.apply {
            setBackgroundResource(R.drawable.shp_default_age_rating_bg)
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
            val layoutParams = LayoutParams(60.dpToPx(), 60.dpToPx())
            if (!isLast) {
                layoutParams.rightMargin = 8.dpToPx()
            }
            setLayoutParams(layoutParams)
        }
    }

    private fun configureSelectedTextView(textView: TextView, isLast: Boolean): TextView {
        return textView.apply {
            setBackgroundResource(R.drawable.shp_selected_age_rating_bg)
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            val layoutParams = LayoutParams(70.dpToPx(), 70.dpToPx())
            if (!isLast) {
                layoutParams.rightMargin = 8.dpToPx()
            }
            setLayoutParams(layoutParams)
        }
    }

    fun moveToNext() {
        if (selectedPosition < size - 1) {
            selectedPosition += 1
        }
    }

    fun moveToPrevious() {
        if (selectedPosition > 0) {
            selectedPosition -= 1
        }
    }
}