package com.zelyder.chilldev.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zelyder.chilldev.domain.enums.AgeLimit
import com.zelyder.chilldev.domain.enums.Gender
import com.zelyder.chilldev.domain.models.AvailableService
import com.zelyder.chilldev.domain.models.Category
import com.zelyder.chilldev.domain.models.KidInfo

class MainViewModel : ViewModel() {
    private val _kidInfo = MutableLiveData(KidInfo())
    val kidInfo: LiveData<KidInfo> get() = _kidInfo

    fun setKidName(name: String) {
        _kidInfo.value?.name = name
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.value?.ageLimit = ageLimit
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.value?.gender = gender
    }

    fun setKidBirthday(birthday: String) {
        _kidInfo.value?.birthday = birthday
    }

    fun setKidCategories(categories: List<Category>) {
        _kidInfo.value?.categories = categories
    }

    fun setKidServices(availableServices: List<AvailableService>) {
        _kidInfo.value?.availableServices = availableServices
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.value?.pinCode = pinCode
    }


}