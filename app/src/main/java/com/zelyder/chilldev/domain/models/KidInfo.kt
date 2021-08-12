package com.zelyder.chilldev.domain.models

import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*

data class KidInfo(
    val name: String = "Anon",
    val age_limit: Int = AgeLimit.ZERO_PLUS.type,
    val gender: String = Gender.WHATEVER.type,
    val birthdate: String = Date().formatToString(),
    val categories: List<Int> = emptyList(),
    val apps: JsonObject = JsonObject(),
    val pin: String? = null
)

fun Date.formatToString(pattern: String = "yyyy-MM-dd"): String {
    val jdf = SimpleDateFormat(pattern, Locale.getDefault())
    return jdf.format(this)
}

fun String.parseToDate(): Date? {
    if (this.isEmpty()) return null
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
}