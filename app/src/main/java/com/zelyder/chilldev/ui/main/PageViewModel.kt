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
        Log.d(TAG, "Set name: $name")
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.postValue(_kidInfo.value!!.copy(age_limit = ageLimit.age))
        Log.d(TAG, "Set age limit: $ageLimit")
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.postValue(_kidInfo.value!!.copy(gender = gender))
        Log.d(TAG, "Set gender: ${gender.name}")
    }

    fun setKidBirthday(birthday: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(birthdate = birthday))
        Log.d(TAG, "Set age birthday: $birthday")
    }

    fun setKidCategories(categories: List<Category>) {
        _kidInfo.postValue(_kidInfo.value!!.copy(categories = categories.map { it.id }))
        Log.d(TAG, "Set categories: $categories")
    }

    fun updateServices(title: String, checked: Boolean) {
        val services = _kidInfo.value!!.apps
        if (checked) {
            services.addProperty(title, checked)
        } else {
            services.remove(title)
        }
        _kidInfo.postValue(_kidInfo.value!!.copy(apps = services))
        Log.d(TAG, "Update services: $services")
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(pin = pinCode))
        Log.d(TAG, "Set pinCode: $pinCode")
    }

    suspend fun getAllKids(): List<Kid> {
        return repository.getAllKids()
    }

    suspend fun saveKidInfo() {
        repository.saveKid(kidInfo.value!!)
    }

    fun setIcon(iconType: KidNameIconType) {
        _kidInfo.postValue(_kidInfo.value!!.copy(iconType = iconType))
        Log.d(TAG, "Added icon: $iconType")
    }

    companion object {
        val TAG = PageViewModel::class.java.canonicalName
    }
}