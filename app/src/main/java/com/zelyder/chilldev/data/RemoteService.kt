package com.zelyder.chilldev.data

import retrofit2.Response
import retrofit2.http.*

interface RemoteService {

    @GET("/posters")
    suspend fun posters(@Query("agelimit") agelimit: ChildAges): Response<com.zelyder.chilldev.data.Response<String>>

    @GET("/categories")
    suspend fun categories(): Response<com.zelyder.chilldev.data.Response<Categories>>

    @POST("/kid_info")
    suspend fun kidInfo(@Body kid: KidInfo)

}