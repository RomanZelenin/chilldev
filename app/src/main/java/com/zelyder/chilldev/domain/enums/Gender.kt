package com.zelyder.chilldev.domain.enums

enum class Gender(val type: String) {
    MALE("male"),
    FEMALE("female"),
    WHATEVER("whatever"); // TODO: Сверить с бэкендом

    companion object {
        fun parse(type: String): Gender {
            return values().find { it.type == type } ?: WHATEVER
        }
    }
}