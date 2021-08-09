package com.zelyder.chilldev.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.JsonObject
import com.zelyder.chilldev.domain.models.*
import kotlinx.coroutines.launch

class PageViewModel(private val remoteService: RemoteService) : ViewModel() {
    private val _kidInfo = MutableLiveData(KidInfo().Builder())
    val kidInfo: LiveData<KidInfo.Builder> get() = _kidInfo

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
        _kidInfo.value!!.setName(name)
        Log.d(TAG, "Added name: $name")
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.value!!.setAgeLimit(ageLimit)
        Log.d(TAG, "Added age limit: $ageLimit")
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.value!!.setGender(gender)
        Log.d(TAG, "Added gender: $gender")
    }

    fun setKidBirthday(birthday: String) {
        _kidInfo.value!!.setBirthDate(birthday)
        Log.d(TAG, "Added age birthday: $birthday")
    }

    fun setKidCategories(categories: List<Category>) {
        _kidInfo.value?.setCategories(categories)
        Log.d(TAG, "Added categories: $categories")
    }

    fun setKidServices(availableServices: List<AvailableService>) {
        _kidInfo.value?.setApps(JsonObject())
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.value!!.setPin(pinCode)
        Log.d(TAG, "Added pinCode: $pinCode")
    }

    fun postKidInfo() {
        viewModelScope.launch {
            try {
                remoteService.kidInfo(_kidInfo.value!!.build())
            }catch (e:Throwable){

            }
        }
    }
}