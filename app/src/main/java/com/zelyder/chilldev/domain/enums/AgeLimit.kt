package com.zelyder.chilldev.domain.enums

enum class AgeLimit(val type: Int) {
    NONE(-1),
    ZERO_PLUS(0),
    SIX_PLUS(6),
    TWELVE_PLUS(12),
    SIXTEEN_PLUS(16),
    EIGHTEEN_PLUS(18);

    companion object {
        fun parse(type: Int): AgeLimit {
            return values().find { it.type == type } ?: NONE
        }
    }
}