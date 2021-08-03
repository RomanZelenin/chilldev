package com.zelyder.chilldev.data

enum class ChildAges(val age: Int) {
    ZERO(0), SIX(6), TWELVE(12), SIXTEEN(16), EIGHTEEN(18);

    override fun toString(): String {
        return age.toString()
    }
}