package com.zelyder.chilldev.domain.models

import com.zelyder.chilldev.domain.enums.AgeLimit
import com.zelyder.chilldev.domain.enums.Gender

data class KidInfo(
    var name: String? = null,
    var ageLimit: AgeLimit = AgeLimit.NONE,
    var gender: Gender = Gender.WHATEVER,
    var birthday: String? = null,
    var categories: List<Category> = emptyList(),
    var availableServices: List<AvailableService> = emptyList(),
    var pinCode: String? = null
)
