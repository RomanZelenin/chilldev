package com.zelyder.chilldev.domain.models

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteService {

    @GET("/posters")
    suspend fun posters(@Query("agelimit") agelimit: AgeLimit): Response<com.zelyder.chilldev.domain.models.Response<String>>

    @GET("/categories")
    suspend fun categories(): Response<com.zelyder.chilldev.domain.models.Response<Category>>

    @POST("/kid_info")
    suspend fun kidInfo(@Body kid: KidInfo)

    @GET("/kids")
    suspend fun kids(): Response<com.zelyder.chilldev.domain.models.Response<Kid>>

    @GET("/kid")
    suspend fun kid(@Query("id") id: Int): Response<com.zelyder.chilldev.domain.models.Response<KidInfo>>
}