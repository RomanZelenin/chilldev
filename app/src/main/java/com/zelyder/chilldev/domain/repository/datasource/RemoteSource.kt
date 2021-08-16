package com.zelyder.chilldev.domain.repository.datasource

import android.net.Uri
import com.zelyder.chilldev.domain.models.AgeLimit
import com.zelyder.chilldev.domain.models.Category
import com.zelyder.chilldev.domain.models.Kid
import com.zelyder.chilldev.domain.models.KidInfo

interface RemoteSource {
    suspend fun getUrlPosters(ageLimit: AgeLimit): List<Uri>

    suspend fun getListCategories(): List<Category>

    suspend fun saveKidInfo(kidInfo: KidInfo)

    suspend fun getAllKids(): List<Kid>

    suspend fun getKidInfo(id: Int): KidInfo?
}
