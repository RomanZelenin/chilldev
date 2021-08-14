package com.zelyder.chilldev.ui.kidname

import android.view.View

interface SearchNextFocusListener {
    fun searchTop(focused: View?): View? = null
    fun searchDown(focused: View?): View? = null
}