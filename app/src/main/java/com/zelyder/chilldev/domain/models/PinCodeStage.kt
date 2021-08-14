package com.zelyder.chilldev.domain.models

enum class PinCodeStage(val type: Int) {
    NEW(0), ENTER(1);

    companion object {

        fun parse(type: Int): PinCodeStage {
            return values().find { it.type == type } ?: NEW
        }

    }
}