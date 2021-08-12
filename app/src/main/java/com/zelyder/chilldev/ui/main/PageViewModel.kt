package com.zelyder.chilldev.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.zelyder.chilldev.domain.models.*
import kotlinx.coroutines.launch

class PageViewModel(private val remoteService: RemoteService) : ViewModel() {

    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> = _kidInfo

    private val _urlPosters = Transformations.switchMap(_kidInfo) {
        val ageLimit = AgeLimit.values().first { ageLimit -> ageLimit.type == it.age_limit }
        getPosters(ageLimit)
    }
    val urlPosters: LiveData<List<String>> = _urlPosters

    var cachedCategories = listOf<Category>(Category(1,"Дружба"))
    fun getCategories() = liveData {
        emit(cachedCategories.map { it.title })
        cachedCategories = remoteService.categories().body()?.message!!
        emit(cachedCategories.map { it.title })
    }

    private fun getPosters(ageLimit: AgeLimit) = liveData {
        val response = remoteService.posters(ageLimit)
        if (response.isSuccessful) {
            val urls = response.body()!!.message
            emit(urls)
        } else {
            Log.d(TAG, response.errorBody()!!.string())
        }
    }


    fun setKidName(name: String) {
        _kidInfo.postValue(_kidInfo.value!!.copy(name = name))
        Log.d(TAG, "Set name: $name")
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.postValue(_kidInfo.value!!.copy(age_limit = ageLimit.type))
        Log.d(TAG, "Set age limit: $ageLimit")
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.postValue(_kidInfo.value!!.copy(gender = gender.type))
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

    fun postKidInfo() {
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