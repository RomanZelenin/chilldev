package com.zelyder.chilldev.data

data class Response<T>(
    val success: Boolean,
    val message: List<T>
)
