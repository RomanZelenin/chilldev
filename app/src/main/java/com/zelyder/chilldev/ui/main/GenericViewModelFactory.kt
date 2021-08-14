package com.zelyder.chilldev.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenericViewModelFactory<out V : ViewModel>(private val viewModelFactory: ViewModelAssistedFactory<V>) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModelFactory.create() as T
    }
}