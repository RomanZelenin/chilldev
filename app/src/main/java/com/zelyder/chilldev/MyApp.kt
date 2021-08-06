package com.zelyder.chilldev

import android.app.Application
import android.content.Context
import com.zelyder.chilldev.ui.core.ViewModelFactory

class MyApp : Application() {
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        viewModelFactory = ViewModelFactory()
    }
}

val Context.viewModelFactory: ViewModelFactory
    get() = when (this) {
        is MyApp -> viewModelFactory
        else -> this.applicationContext.viewModelFactory
    }