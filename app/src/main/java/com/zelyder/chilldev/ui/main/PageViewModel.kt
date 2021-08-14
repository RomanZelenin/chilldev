package com.zelyder.chilldev.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zelyder.chilldev.domain.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PageViewModel(private val remoteService: RemoteService) : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>>
        get() = _categories

    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> = _kidInfo

    private val cachedCategories = mutableListOf<String>()
    private val cachedPosters = mutableMapOf<AgeLimit, List<String>>()

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    remoteService.categories().body()?.message!!.map { it.title }
                }
                cachedCategories.apply {
                    clear()
                    addAll(result)
                }
                withContext(Dispatchers.Main) {
                    _categories.value = result
                }
            } catch (e: Throwable) {
                Log.d(TAG, "categories: ${e.message}")
            } finally {
                _categories.value = cachedCategories
            }
        }
    }

    suspend fun getPosters(ageLimit: AgeLimit): List<String> {
        try {
            val response = remoteService.posters(ageLimit)
            return if (response.isSuccessful) {
                val urls = response.body()!!.message
                cachedPosters[ageLimit] = urls
                urls
            } else {
                Log.d(TAG, response.errorBody()!!.string())
                emptyList()
            }
        } catch (e: Throwable) {
            Log.d(TAG, "posters: ${e.message}")
        }
        return cachedPosters[ageLimit] ?: emptyList()
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

    fun setKidServices(availableServices: List<AvailableService>) {
        //   _kidInfo.value?.setApps(JsonObject())
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(pin = pinCode))
        Log.d(TAG, "Set pinCode: $pinCode")
    }

    fun setIcon(iconType: KidNameIconType) {
        _kidInfo.postValue(_kidInfo.value!!.copy(iconType = iconType))
        Log.d(TAG, "Added icon: $iconType")
    }

    fun saveKidInfo() {
        viewModelScope.launch {
            try {
                remoteService.kidInfo(_kidInfo.value!!)
            } catch (e: Throwable) {

            }
        }
    }

    companion object {
        val TAG = PageViewModel::class.java.canonicalName
    }
}