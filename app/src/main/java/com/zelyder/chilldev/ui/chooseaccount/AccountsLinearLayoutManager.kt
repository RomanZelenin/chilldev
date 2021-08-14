package com.zelyder.chilldev.ui.chooseaccount

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.customview.AccountView
import com.zelyder.chilldev.customview.AddProfileView

class AccountsLinearLayoutManager(
    context : Context
) : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {

    private var previousView: View? = null
    private var position : Int = -1

    override fun onRequestChildFocus(
        parent: RecyclerView,
        state: RecyclerView.State,
        child: View,
        focused: View?
    ): Boolean {
        val flg = super.onRequestChildFocus(parent, state, child, focused)
        if (previousView != null) {
            if (previousView != focused) {
                when (previousView) {
                    is AddProfileView ->{
                        (previousView as AddProfileView).onFocusChange(previousView, false)
                    }
                    is AccountView ->{
                        (previousView as AccountView).onFocusChange(previousView, false)
                    }
                }
            }
        }
        previousView = focused
        when (focused) {
            is AddProfileView -> focused.onFocusChange(focused, true)
            is AccountView -> focused.onFocusChange(focused, true)
        }
        position = parent.getChildLayoutPosition(focused!!)
        return flg
    }
}