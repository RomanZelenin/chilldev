package com.zelyder.chilldev.di

import android.content.Context
import com.zelyder.chilldev.YandexKidTvApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Scope
annotation class ApplicationScope

@Component(modules = [NetworkModule::class, InterceptorsModule::class, DataSources::class])
@ApplicationScope
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun createActivitySubcomponent(): ActivityComponent.Factory

    fun inject(application: YandexKidTvApplication)
}