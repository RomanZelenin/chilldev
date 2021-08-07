package com.zelyder.chilldev.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.JsonObject
import com.zelyder.chilldev.domain.models.*
import kotlinx.coroutines.launch

class PageViewModel(private val remoteService: RemoteService) : ViewModel() {
    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> get() = _kidInfo

    companion object {
        val TAG = PageViewModel::class.java.canonicalName
    }


    val categories = liveData {
        emit(
            listOf(
                Category(1, "Я познаю мир"),
                Category(2, "Спорт"),
            )
        )
        emit(remoteService.categories().body()?.message!!)
    }


    fun setKidName(name: String) {
        _kidInfo.value?.name = name
        Log.d(TAG, "Added name: $name")
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.value?.age_limit = ageLimit.type
        Log.d(TAG, "Added age limit: $ageLimit")
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.value?.gender = gender.type.lowercase()
        Log.d(TAG, "Added gender: $gender")
    }

    fun setKidBirthday(birthday: String) {
        _kidInfo.value?.birthdate = birthday
        Log.d(TAG, "Added age birthday: $birthday")
    }

    fun setKidCategories(categories: List<Category>) {
        _kidInfo.value?.categories = categories.map { it.id }
        Log.d(TAG, "Added categories: $categories")
    }

    fun setKidServices(availableServices: List<AvailableService>) {
        _kidInfo.value?.apps = JsonObject()
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.value?.pin = pinCode
        Log.d(TAG, "Added pinCode: $pinCode")
    }

    fun postKidInfo() {
        viewModelScope.launch {
            remoteService.kidInfo(_kidInfo.value!!)
        }
    }
}