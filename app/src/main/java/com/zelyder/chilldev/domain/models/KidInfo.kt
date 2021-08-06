package com.zelyder.chilldev.domain.models

data class KidInfo(
    var name: String? = null,
    var age_limit: AgeLimit = AgeLimit.NONE,
    var gender: Gender = Gender.WHATEVER,
    var birthdate:String? = null,
    var categories: List<Category> = emptyList(),
    var app: List<AvailableService> = emptyList(),
    var pin:String? = null
)
