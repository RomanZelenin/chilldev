package com.zelyder.chilldev.ui.interests

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zelyder.chilldev.customview.InterestView
import com.zelyder.chilldev.ui.main.SwipePage

class InterestsStaggeredLayoutManager(
    spanCount: Int,
    orientation: Int,
    private val page: SwipePage
) : StaggeredGridLayoutManager(spanCount, orientation) {

    private var previousView: InterestView? = null

    override fun onInterceptFocusSearch(focused: View, direction: Int): View? {
        val view = super.onInterceptFocusSearch(focused, direction)

        val layoutParams = focused.layoutParams as? LayoutParams
        layoutParams?.spanIndex?.let { spanIndex ->
            when (direction) {
                View.FOCUS_DOWN -> {
                    if (spanIndex == spanCount - 1) {
                        page.swipeToNext()
                    }
                }
                View.FOCUS_UP -> {
                    if (spanIndex == 0) {
                        page.swipeToPrevious()
                    }
                }
            }
        }
        return view
    }

    override fun onRequestChildFocus(
        parent: RecyclerView,
        state: RecyclerView.State,
        child: View,
        focused: View?
    ): Boolean {
        val flg = super.onRequestChildFocus(parent, state, child, focused)
        if (previousView != null) {
            if (previousView != focused) {
                previousView?.checked?.let { previousView?.onFocusChange(previousView, false) }
            }
        }
        previousView = focused as InterestView
        focused.onFocusChange(focused, true)
        return flg
    }
}