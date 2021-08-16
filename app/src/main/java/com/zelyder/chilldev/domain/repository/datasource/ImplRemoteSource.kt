package com.zelyder.chilldev.domain.repository.datasource

import android.net.Uri
import android.util.Log
import com.zelyder.chilldev.domain.models.AgeLimit
import com.zelyder.chilldev.domain.models.Category
import com.zelyder.chilldev.domain.models.Kid
import com.zelyder.chilldev.domain.models.KidInfo
import javax.inject.Inject

class ImplRemoteSource @Inject constructor(private val remoteService: RemoteService) :
    RemoteSource {
    private val cachedUrlPosters = mutableMapOf<AgeLimit, List<Uri>>()

    override suspend fun getUrlPosters(ageLimit: AgeLimit): List<Uri> {
        try {
            val response = remoteService.posters(ageLimit)
            return if (response.isSuccessful && response.body()!!.success) {
                response.body()!!.message.map { Uri.parse(it) }.apply {
                    cachedUrlPosters[ageLimit] = this
                }
            } else {
                Log.d(TAG, response.errorBody()!!.string())
                emptyList()
            }
        } catch (e: Throwable) {
            Log.d(TAG, "${e.message}")
        }
        return cachedUrlPosters[ageLimit] ?: emptyList()
    }

    override suspend fun getListCategories(): List<Category> {
        try {
            val response = remoteService.categories()
            return if (response.isSuccessful && response.body()!!.success) {
                return response.body()!!.message
            } else {
                Log.d(TAG, response.errorBody()!!.string())
                emptyList()
            }
        } catch (e: Throwable) {
            Log.d(TAG, "${e.message}")
        }
        return emptyList()
    }

    override suspend fun saveKidInfo(kidInfo: KidInfo) {
        try {
            remoteService.kidInfo(kidInfo)
        } catch (e: Throwable) {
            Log.d(TAG, "${e.message}")
        }
    }

    override suspend fun getAllKids(): List<Kid> {
        try {
            val response = remoteService.kids()
            return if (response.isSuccessful && response.body()!!.success) {
                return response.body()?.message!!
            } else {
                Log.d(TAG, response.errorBody()!!.string())
                emptyList()
            }
        } catch (e: Throwable) {
            Log.d(TAG, "${e.message}")
        }
        return emptyList()
    }

    override suspend fun getKidInfo(id: Int): KidInfo? {
        try {
            val response = remoteService.kid(id)
            return if (response.isSuccessful && response.body()!!.success) {
                response.body()?.message!!
            } else {
                Log.d(TAG, response.errorBody()!!.string())
                null
            }
        } catch (e: Throwable) {
            Log.d(TAG, "${e.message}")
        }
        return null
    }

    companion object {
        val TAG = ImplRemoteSource::class.java.name
    }
}