package com.zelyder.chilldev.domain.models

import com.google.gson.annotations.SerializedName

enum class AgeLimit(val age: Int) {
    @SerializedName("0")
    ZERO_PLUS(0),
    @SerializedName("6")
    SIX_PLUS(6),
    @SerializedName("12")
    TWELVE_PLUS(12),
    @SerializedName("16")
    SIXTEEN_PLUS(16),
    @SerializedName("18")
    EIGHTEEN_PLUS(18);

    override fun toString(): String {
        return age.toString()
    }

    fun toUIString(): String {
        return "+$age"
    }
}