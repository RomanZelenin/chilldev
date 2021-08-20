package com.zelyder.chilldev.ui.addapp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.customview.AppView
import com.zelyder.chilldev.ui.main.SwipePage

class AppLinearLayoutManager(context : Context, private val page: SwipePage) : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {

    private var previousView: AppView? = null
    private var position : Int = -1

    override fun onInterceptFocusSearch(focused: View, direction: Int): View? {
        val view = super.onInterceptFocusSearch(focused, direction)
        when (direction) {
            View.FOCUS_DOWN -> {
                if (position == itemCount-1) {
                    page.swipeToNext()
                }
            }
            View.FOCUS_UP -> {
                if (position == 0) {
                    page.swipeToPrevious()
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
        if (focused is AppView) {
            previousView = focused
            focused.onFocusChange(focused, true)
            position = parent.getChildLayoutPosition(focused)
        }
        return flg
    }
}