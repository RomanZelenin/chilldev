package com.zelyder.chilldev.domain.repository

import android.net.Uri
import com.zelyder.chilldev.domain.models.AgeLimit
import com.zelyder.chilldev.domain.models.Kid
import com.zelyder.chilldev.domain.models.KidInfo
import com.zelyder.chilldev.domain.repository.datasource.LocalSource
import com.zelyder.chilldev.domain.repository.datasource.RemoteSource
import javax.inject.Inject


class Repository @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) {

    suspend fun getPosters(ageLimit: AgeLimit): List<Uri> {
        return remoteSource.getUrlPosters(ageLimit)
    }

    suspend fun getCategories(): List<String> {
        return remoteSource.getListCategories().map { it.title }
    }

    suspend fun getAllKids(): List<Kid> {
        return remoteSource.getAllKids()
    }

    suspend fun saveKid(kidInfo: KidInfo){
        remoteSource.saveKidInfo(kidInfo)
    }

    fun setPinCode(pinCode: String) {
        localSource.setPinCode(pinCode)
    }

    fun getPinCode(): String? {
        return localSource.getPinCode()
    }
}