package com.zelyder.chilldev.ui.main

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.zelyder.chilldev.domain.models.*
import com.zelyder.chilldev.domain.repository.Repository
import timber.log.Timber

class PageViewModel(private val repository: Repository) :
    ViewModel() {

    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> = _kidInfo

    val categories = liveData {
            val categories = repository.getCategories()
            emit(categories)
    }

    suspend fun getPosters(ageLimit: AgeLimit): List<Uri> {
        return repository.getPosters(ageLimit)
    }


    fun setKidName(name: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(name = name))
        Timber.d("Set name: $name")
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.postValue(_kidInfo.value!!.copy(age_limit = ageLimit.age))
        Timber.d("Set age limit: $ageLimit")
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.postValue(_kidInfo.value!!.copy(gender = gender))
        Timber.d("Set gender: ${gender.name}")
    }

    fun setKidBirthday(birthday: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(birthdate = birthday))
        Timber.d("Set age birthday: $birthday")
    }

    fun setKidCategories(categories: List<Category>) {
        _kidInfo.postValue(_kidInfo.value!!.copy(categories = categories.map { it.id }))
        Timber.d("Set categories: $categories")
    }

    fun updateServices(title: String, checked: Boolean) {
        val services = _kidInfo.value!!.apps
        if (checked) {
            services.addProperty(title, checked)
        } else {
            services.remove(title)
        }
        _kidInfo.postValue(_kidInfo.value!!.copy(apps = services))
        Timber.d("Update services: $services")
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(pin = pinCode))
        repository.setPinCode(pinCode)
        Timber.d("Set pinCode: $pinCode")
    }

    suspend fun getAllKids(): List<Kid> {
        return repository.getAllKids()
    }

    suspend fun saveKidInfo() {
        repository.saveKid(kidInfo.value!!)
    }

    fun setIcon(iconType: KidIcon) {
        _kidInfo.postValue(_kidInfo.value!!.copy(iconType = iconType, avatar = iconType.rank.toString()))
        Timber.d("Added icon: ${iconType.rank}")
    }

    fun getPinCode():String? {
       return repository.getPinCode()
    }
}