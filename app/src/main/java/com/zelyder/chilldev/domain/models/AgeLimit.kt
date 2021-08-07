package com.zelyder.chilldev.domain.models

enum class AgeLimit(val type: Int) {
    NONE(-1),
    ZERO_PLUS(0),
    SIX_PLUS(6),
    TWELVE_PLUS(12),
    SIXTEEN_PLUS(16),
    EIGHTEEN_PLUS(18);

    override fun toString(): String {
        return type.toString()
    }
}