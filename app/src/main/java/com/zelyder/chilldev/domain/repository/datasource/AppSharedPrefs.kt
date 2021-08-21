package com.zelyder.chilldev.domain.repository.datasource

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPrefs @Inject constructor(
    context: Context
) {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    var pinCode: String?
        get() = sharedPrefs.getString(PIN_CODE, null)
        set(value) = sharedPrefs.edit().putString(PIN_CODE, value).apply()

    companion object {
        const val SHARED_PREFS_NAME = "ChilldevPrefs"
        const val PIN_CODE = "PIN_CODE"
    }
}