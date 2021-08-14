package com.zelyder.chilldev.ui.main

import com.zelyder.chilldev.domain.models.RemoteService
import javax.inject.Inject

class PageViewModelFactory @Inject constructor(private val remoteService: RemoteService) :
    ViewModelAssistedFactory<PageViewModel> {
    override fun create(): PageViewModel {
        return PageViewModel(remoteService)
    }
}
