package com.zelyder.chilldev.domain.models

import com.zelyder.chilldev.domain.enums.AgeLimit
import com.zelyder.chilldev.domain.enums.Gender

data class Kid(
    var name: String? = null,
    var ageLimit: AgeLimit = AgeLimit.NONE,
    var gender: Gender = Gender.WHATEVER,
    var birthdate: String? = null,
    var categories: List<Category> = emptyList(),
    var childAccessList: List<ChildAccess> = emptyList(),
    var pinCode: String? = null
)
