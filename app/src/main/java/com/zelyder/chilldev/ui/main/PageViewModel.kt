package com.zelyder.chilldev.ui.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.zelyder.chilldev.domain.repository.Repository
import com.zelyder.chilldev.domain.models.*
import kotlinx.coroutines.launch
import timber.log.Timber

class PageViewModel(private val repository: Repository) :
    ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> = _kidInfo

    fun getCategories(): LiveData<List<String>> {
        viewModelScope.launch {
            val categories = repository.getCategories()
            _categories.postValue(categories)
        }
        return _categories
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

    fun setKidServices(availableServices: List<AvailableService>) {
        val services = JsonObject().apply {
            addProperty("Дублирование экрана", true)
            addProperty("Медиаплеер", true)
        }
        _kidInfo.postValue(_kidInfo.value!!.copy(apps = services))
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(pin = pinCode))
        Timber.d("Set pinCode: $pinCode")
    }

    suspend fun getAllKids(): List<Kid> {
        return repository.getAllKids()
    }

    suspend fun saveKidInfo() {
        repository.saveKid(kidInfo.value!!)
    }

    fun setIcon(iconType: KidNameIconType) {
        _kidInfo.postValue(_kidInfo.value!!.copy(iconType = iconType))
        Timber.d("Added icon: $iconType")
    }
}