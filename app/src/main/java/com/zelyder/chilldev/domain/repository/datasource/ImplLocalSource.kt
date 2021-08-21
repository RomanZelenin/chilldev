package com.zelyder.chilldev.domain.repository.datasource

import javax.inject.Inject

class ImplLocalSource @Inject constructor(
    private val sharedPrefs: AppSharedPrefs
) : LocalSource {

    override fun setPinCode(pinCode: String) {
        sharedPrefs.pinCode = pinCode
    }

    override fun getPinCode() = sharedPrefs.pinCode
}