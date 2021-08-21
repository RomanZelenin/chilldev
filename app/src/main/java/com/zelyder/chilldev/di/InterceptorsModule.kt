package com.zelyder.chilldev.di

import com.zelyder.chilldev.BuildConfig
import com.zelyder.chilldev.domain.models.Token
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalInterceptor

@Module
object InterceptorsModule {
    @Provides
    @LocalInterceptor
    @ApplicationScope
    fun provideLocalInterceptors(token: Token): List<Interceptor> {
        val interceptors = mutableListOf<Interceptor>()
        return interceptors.apply {
            add(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader(
                    "Authorization",
                    "OAuth ${token.value}"
                )
                chain.proceed(requestBuilder.build())
            })
        }
    }

    @Provides
    @NetworkInterceptor
    @ApplicationScope
    fun provideNetworkInterceptors(): List<Interceptor> {
        val interceptors = mutableListOf<Interceptor>()
        return if (BuildConfig.DEBUG) {
            return interceptors.apply {
                add(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            }
        } else {
            interceptors
        }
    }
}

fun OkHttpClient.Builder.addInterceptors(
    interceptors: Iterable<Interceptor>,
    isNetwork: Boolean
): OkHttpClient.Builder {
    if (!isNetwork) {
        interceptors.forEach { interceptor ->
            addInterceptor(interceptor)
        }
    } else {
        interceptors.forEach { interceptor ->
            addNetworkInterceptor(interceptor)
        }
    }
    return this
}
