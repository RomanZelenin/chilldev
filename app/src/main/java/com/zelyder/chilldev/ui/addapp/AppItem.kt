package com.zelyder.chilldev.ui.addapp

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes


data class AppItem(
    val name: String,
    @ColorRes
    val color: Int,
    val checked: Boolean,
    val icon:Drawable
)