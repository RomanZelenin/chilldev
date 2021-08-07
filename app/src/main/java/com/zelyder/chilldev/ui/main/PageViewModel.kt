package com.zelyder.chilldev.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zelyder.chilldev.domain.models.AgeLimit
import com.zelyder.chilldev.domain.models.Category
import com.zelyder.chilldev.domain.models.Gender
import com.zelyder.chilldev.domain.models.KidInfo
import com.zelyder.chilldev.domain.models.AvailableService

class PageViewModel : ViewModel() {
    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> get() = _kidInfo

    companion object {
        val TAG = PageViewModel::class.java.canonicalName
    }


    fun getCategories(): List<Category> {
      return  listOf(
            Category(1, "Я познаю мир"),
            Category(2, "Спорт"),
            Category(3, "Приключения"),
            Category(4, "На английском языке"),
            Category(5, "Про дружбу"),
            Category(6, "Животные")
        )
    }

    fun setKidName(name: String) {
        _kidInfo.value?.name = name
        Log.d(TAG, "Added name: $name")
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.value?.age_limit = ageLimit
        Log.d(TAG, "Added age limit: $ageLimit")
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.value?.gender = gender
        Log.d(TAG, "Added age limit: $gender")
    }

    fun setKidBirthday(birthday: String) {
        _kidInfo.value?.birthdate = birthday
        Log.d(TAG, "Added age birthday: $birthday")
    }

    fun setKidCategories(categories: List<Category>) {
        _kidInfo.value?.categories = categories
        Log.d(TAG, "Added age categories: $categories")
    }

    fun setKidServices(availableServices: List<AvailableService>) {
        _kidInfo.value?.app = availableServices
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.value?.pin = pinCode
        Log.d(TAG, "Added age pinCode: $pinCode")
    }

    fun postKidInfo(){

    }
}