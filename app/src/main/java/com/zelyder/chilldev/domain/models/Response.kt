package com.zelyder.chilldev.domain.models

data class Response<T>(
    val success: Boolean,
    val message: List<T>
)
