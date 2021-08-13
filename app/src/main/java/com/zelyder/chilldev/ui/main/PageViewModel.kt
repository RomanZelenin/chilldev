package com.zelyder.chilldev.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.zelyder.chilldev.domain.models.*
import kotlinx.coroutines.launch

class PageViewModel(private val remoteService: RemoteService) : ViewModel() {

    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> = _kidInfo

    val categories = liveData {
        remoteService.categories()
            .body()
            ?.message!!
            .map { it.title }
            .let { emit(it) }
    }

    suspend fun getPosters(ageLimit: AgeLimit): List<String> {
        val response = remoteService.posters(ageLimit)
        return if (response.isSuccessful) {
            val urls = response.body()!!.message
            urls
        } else {
            Log.d(TAG, response.errorBody()!!.string())
            emptyList()
        }
    }

    fun setKidName(name: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(name = name))
        Log.d(TAG, "Set name: $name")
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.postValue(_kidInfo.value!!.copy(age_limit = ageLimit))
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