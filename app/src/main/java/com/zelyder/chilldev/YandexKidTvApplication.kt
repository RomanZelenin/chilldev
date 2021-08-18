package com.zelyder.chilldev

import android.app.Application
import timber.log.Timber

class YandexKidTvApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}