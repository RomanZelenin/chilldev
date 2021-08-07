package com.zelyder.chilldev.domain.models

import com.google.gson.JsonObject

data class KidInfo(
    var name: String? = null,
    var age_limit: Int = -1,
    var gender: String = "",
    var birthdate:String? = null,
    var categories: List<Int> = emptyList(),
    var apps:JsonObject = JsonObject(),
    var pin:String? = null
)
