package com.zelyder.chilldev.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zelyder.chilldev.domain.models.RemoteService
import com.zelyder.chilldev.ui.FragmentPage
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class PageViewModelFactory(val remoteService: RemoteService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        PageViewModel::class.java -> PageViewModel(remoteService)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}