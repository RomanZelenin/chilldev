package com.zelyder.chilldev.di

import com.zelyder.chilldev.domain.repository.datasource.*
import dagger.Binds
import dagger.Module

@Module
interface DataSources {
    @Binds
    @ApplicationScope
    fun getRemoteSource(implRemoteSource: ImplRemoteSource): RemoteSource

    @Binds
    @ApplicationScope
    fun getLocalSource(implLocalSource: ImplLocalSource): LocalSource
}