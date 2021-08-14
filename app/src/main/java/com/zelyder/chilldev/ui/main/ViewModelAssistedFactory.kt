package com.zelyder.chilldev.ui.main

import androidx.lifecycle.ViewModel

interface ViewModelAssistedFactory<T : ViewModel> {
    fun create(): T
}