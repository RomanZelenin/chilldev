package com.zelyder.chilldev.domain.models

enum class Gender(val type: String) {
    MALE("male"),
    FEMALE("female"),
    WHATEVER("whatever"); // TODO: Сверить с бэкендом

    override fun toString(): String {
        return type
    }
}