package com.zelyder.chilldev.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zelyder.chilldev.ui.FragmentPage
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T  = when(modelClass){
        FragmentPage::class.java -> PageViewModel()
        else ->throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}