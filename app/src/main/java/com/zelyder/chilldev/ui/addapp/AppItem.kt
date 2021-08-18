package com.zelyder.chilldev.ui.addapp

import androidx.annotation.ColorRes


data class AppItem(
    val name: String,
    @ColorRes
    val color: Int,
    val checked: Boolean
)