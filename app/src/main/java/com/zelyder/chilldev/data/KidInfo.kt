package com.zelyder.chilldev.data

data class KidInfo(
    val name: String,
    val age_limit: String,
    val gender: String,
    val birthdate:String,
    val categories: List<Int>,
    //val apps:
    val pin:String
)
