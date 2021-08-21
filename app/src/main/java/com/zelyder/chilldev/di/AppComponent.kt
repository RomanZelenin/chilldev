package com.zelyder.chilldev.di

import com.zelyder.chilldev.domain.models.Token
import com.zelyder.chilldev.ui.chooseaccount.ChooseAccountActivity
import com.zelyder.chilldev.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Scope
annotation class ApplicationScope

@Component(modules = [NetworkModule::class, InterceptorsModule::class, DataSources::class])
@ApplicationScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance token: Token): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(activity: ChooseAccountActivity)
}
