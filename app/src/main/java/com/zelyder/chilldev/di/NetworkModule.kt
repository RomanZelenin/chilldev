package com.zelyder.chilldev.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zelyder.chilldev.data.RemoteService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    val baseUrl = "https://mywebflaskapp.com/"

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            /*.connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader("Authorization", "Bearer $token")
                chain.proceed(requestBuilder.build())
            }*/.build()
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