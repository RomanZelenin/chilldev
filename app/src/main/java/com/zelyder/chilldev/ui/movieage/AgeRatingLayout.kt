package com.zelyder.chilldev.ui.movieage

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.zelyder.chilldev.R
import com.zelyder.chilldev.extensions.dpToPx

class AgeRatingLayout(context: Context, attrSet: AttributeSet?) :
    LinearLayout(context, attrSet) {

    private val size = AgeRating.values().size

    var selectedPosition = 0
        set(value) {
            field = value
            val startPosition = 0
            val endPosition = size - 1
            for (i in selectedPosition + 1..endPosition) {
                (this[i] as TextView).apply {
                    configureDefaultTextView(this, i)
                }
            }
            for (i in startPosition..selectedPosition) {
                (this[i] as TextView).apply {
                    configureSelectedTextView(this, i)
                }
            }
        }

    init {
        removeAllViews()
        for (i in 0 until size) {
            val tvAgeRating = TextView(context).apply {
                configureDefaultTextView(this, i)
                gravity = Gravity.CENTER
                textSize = 12.dpToPx().toFloat()
                text = AgeRating.values()[i].rating
            }
            addView(tvAgeRating)
        }
        selectedPosition = 0
    }

    private fun configureSelectedTextView(textView: TextView, position: Int): TextView {
        return textView.apply {
            when {
                selectedPosition == 0 -> {
                    setBackgroundResource(R.drawable.shp_rounded_selected_age_rating_bg)
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                }
                position == 0 -> {
                    setBackgroundResource(R.drawable.shp_left_rounded_selected_age_rating_bg)
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                }
                position != selectedPosition -> {
                    setBackgroundResource(R.drawable.shp_selected_age_rating_bg)
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                }
                else -> {
                    setBackgroundResource(R.drawable.shp_right_rounded_selected_age_rating_bg)
                    setTextColor(ContextCompat.getColor(context, android.R.color.black))
                }
            }
        }
    }

    private fun configureDefaultTextView(textView: TextView, idx: Int): TextView {
        return textView.apply {
            setBackgroundResource(R.drawable.shp_default_age_rating_bg)
            setTextColor(ContextCompat.getColor(context, android.R.color.white))
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