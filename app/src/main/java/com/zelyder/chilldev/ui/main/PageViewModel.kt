package com.zelyder.chilldev.ui.main

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

    fun setKidName(name: String) {
        _kidInfo.value?.name = name
    }

    fun setKidAgeLimit(ageLimit: AgeLimit) {
        _kidInfo.value?.age_limit = ageLimit
    }

    fun setKidGender(gender: Gender) {
        _kidInfo.value?.gender = gender
    }

    fun setKidBirthday(birthday: String) {
        _kidInfo.value?.birthdate = birthday
    }

    fun setKidCategories(categories: List<Category>) {
        _kidInfo.value?.categories = categories
    }

    fun setKidServices(availableServices: List<AvailableService>) {
        _kidInfo.value?.app = availableServices
    }

    fun setPinCode(pinCode: String) {
        _kidInfo.value?.pin = pinCode
    }
}