package com.zelyder.chilldev.di

import com.zelyder.chilldev.ui.chooseaccount.ChooseAccountActivity
import com.zelyder.chilldev.ui.main.MainActivity
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class ActivityScope

@Subcomponent
@ActivityScope
interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun inject(activity: MainActivity)

    fun inject(activity: ChooseAccountActivity)
}
