package com.zelyder.chilldev.domain.repository.datasource

interface LocalSource {

    fun setPinCode(pinCode: String)

    fun getPinCode(): String?
}