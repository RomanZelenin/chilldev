package com.zelyder.chilldev

import android.app.Application
import com.zelyder.chilldev.di.ApplicationComponent
import com.zelyder.chilldev.di.DaggerApplicationComponent
import timber.log.Timber

class YandexKidTvApplication : Application() {

    lateinit var appComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
}