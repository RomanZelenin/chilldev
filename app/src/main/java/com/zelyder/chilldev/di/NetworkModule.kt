package com.zelyder.chilldev.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zelyder.chilldev.domain.models.RemoteService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    private const val baseUrl = "https://mywebflaskapp.com/"
    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        @LocalInterceptor interceptors: List<@JvmSuppressWildcards Interceptor>,
        @NetworkInterceptor networkInterceptors: List<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptors(interceptors, false)
            .addInterceptors(networkInterceptors, true)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideService(retrofit: Retrofit): RemoteService {
        return retrofit.create(RemoteService::class.java)
    }
}


