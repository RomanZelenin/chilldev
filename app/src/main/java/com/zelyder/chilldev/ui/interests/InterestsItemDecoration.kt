package com.zelyder.chilldev.ui.interests

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.extensions.dpToPx

class InterestsItemDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val space = 12.dpToPx()
        outRect.apply {
            bottom = space
            left = space
            right = space
            top = space
        }
    }
}