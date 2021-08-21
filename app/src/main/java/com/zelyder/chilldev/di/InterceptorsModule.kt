package com.zelyder.chilldev.di

import android.content.Context
import com.yandex.tv.services.passport.PassportProviderSdk
import com.zelyder.chilldev.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
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
    fun provideLocalInterceptors(context: Context): List<Interceptor> {
        val interceptors = mutableListOf<Interceptor>()
        return interceptors.apply {
            add(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                fun getAccessToken(callback: (String?) -> Unit) {
                    val passportProviderSdk = PassportProviderSdk(context)
                    try {
                        callback(passportProviderSdk.currentAccount?.token)
                    } catch (e: Exception) {
                        callback("AQAAAAANz-MGAAX_m-59qwupY0EhlUjn265cWDg")
                        Timber.e(e, "Cannot get access token")
                    }
                }
                getAccessToken { token ->
                    requestBuilder.addHeader(
                        "Authorization",
                        "OAuth $token"
                    )
                }
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