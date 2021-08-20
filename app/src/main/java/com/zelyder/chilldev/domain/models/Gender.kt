package com.zelyder.chilldev.domain.models

import com.google.gson.annotations.SerializedName

enum class Gender(val gender: String) {
    @SerializedName("male")
    MALE("male"),
    @SerializedName("female")
    FEMALE("female"),
    @SerializedName("whatever")
    WHATEVER("whatever");

    override fun toString(): String {
        return gender
    }
}