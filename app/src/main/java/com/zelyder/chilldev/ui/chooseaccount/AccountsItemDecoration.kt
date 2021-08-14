package com.zelyder.chilldev.ui.chooseaccount

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.extensions.dpToPx

class AccountsItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val space = 24.dpToPx()
        outRect.apply {
            right = space
        }
    }
}