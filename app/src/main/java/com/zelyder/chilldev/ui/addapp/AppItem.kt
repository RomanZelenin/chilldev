package com.zelyder.chilldev.ui.addapp

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes


data class AppItem(
    val name: String,
    val checked: Boolean,
    val icon:Drawable
)