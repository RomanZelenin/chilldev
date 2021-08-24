package com.zelyder.chilldev.domain.models

import com.google.gson.JsonObject
import com.zelyder.chilldev.R
import java.text.SimpleDateFormat
import java.util.*

data class KidInfo(
    val name: String = "",
    val age_limit:  Int= AgeLimit.ZERO_PLUS.age,
    val gender: Gender = Gender.WHATEVER,
    val birthdate: String = "",
    val categories: List<Int> = emptyList(),
    val apps: JsonObject = JsonObject(),
    val pin: String? = null,
    var iconType: KidIcon = KidIcon.ONE,
    var avatar: String = R.drawable.ic_avas1.toString()
)

data class Kid(
    val id: Int,
    val avatar: String,
    val name: String
)

fun Date.formatToString(pattern: String = "yyyy-MM-dd"): String {
    val jdf = SimpleDateFormat(pattern, Locale.getDefault())
    return jdf.format(this)
}

fun String.parseToDate(): Date? {
    if (this.isEmpty()) return null
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
}