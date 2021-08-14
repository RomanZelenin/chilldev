package com.zelyder.chilldev.domain.models

import com.google.gson.JsonObject

data class KidInfo(
    var name: String? = null,
    var age_limit: Int = 0,
    var gender: String = "",
    var birthdate: String? = null,
    var categories: List<Int> = emptyList(),
    var apps: JsonObject = JsonObject(),
    var pin: String? = null,
    var iconType: KidNameIconType = KidNameIconType.ONE
) {
    inner class Builder {

        fun setName(name: String):Builder{
            this@KidInfo.name = name
            return this
        }

        fun setAgeLimit(ageLimit: AgeLimit):Builder{
            this@KidInfo.age_limit = ageLimit.type
            return this
        }

        fun setGender(gender: Gender):Builder{
            this@KidInfo.gender = gender.type.lowercase()
            return this
        }

        fun setBirthDate(date:String):Builder{
            this@KidInfo.birthdate = date
            return this
        }

        fun setCategories(categories: List<Category>):Builder{
            this@KidInfo.categories = categories.map { it.id }
            return this
        }

        fun setApps(jsonObject: JsonObject):Builder{
            this@KidInfo.apps = jsonObject
            return this
        }

        fun setPin(pin: String):Builder{
            this@KidInfo.pin = pin
            return this
        }

        fun setIcon(iconType: KidNameIconType):Builder {
            this@KidInfo.iconType = iconType
            return this
        }

        fun build():KidInfo{
            return this@KidInfo
        }

    }
}
