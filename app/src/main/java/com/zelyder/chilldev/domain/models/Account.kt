package com.zelyder.chilldev.domain.models

import com.google.gson.annotations.SerializedName

data class Account(

    @SerializedName("name")
    val name: String,

    @SerializedName("avatar")
    val avatar: Int,

    val checked: Boolean = false
)