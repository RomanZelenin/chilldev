package com.zelyder.chilldev.di

import com.zelyder.chilldev.domain.repository.datasource.ImplRemoteSource
import com.zelyder.chilldev.domain.repository.datasource.RemoteSource
import dagger.Binds
import dagger.Module

@Module
interface DataSources {
    @Binds
    @ApplicationScope
    fun getRemoteSource(implRemoteSource: ImplRemoteSource): RemoteSource
}