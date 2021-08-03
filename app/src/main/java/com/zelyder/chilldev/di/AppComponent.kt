package com.zelyder.chilldev.di

import android.app.Application
import com.zelyder.chilldev.MainActivity
import com.zelyder.chilldev.YandexKidTvApplication
import dagger.Component
import javax.inject.Scope

@Scope
annotation class ApplicationScope

@Component(modules = [NetworkModule::class])
@ApplicationScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun inject(activity: MainActivity)
}