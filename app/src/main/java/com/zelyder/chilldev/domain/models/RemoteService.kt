package com.zelyder.chilldev.domain.models

import retrofit2.Response
import retrofit2.http.*

interface RemoteService {

    @GET("/posters")
    suspend fun posters(@Query("agelimit") agelimit: AgeLimit): Response<com.zelyder.chilldev.domain.models.Response<String>>

    @GET("/categories")
    suspend fun categories(): Response<com.zelyder.chilldev.domain.models.Response<Category>>

    @POST("/kid_info")
    suspend fun kidInfo(@Body kid: KidInfo)

}