package com.zelyder.chilldev.ui.main

import com.zelyder.chilldev.domain.repository.Repository
import javax.inject.Inject

class PageViewModelFactory @Inject constructor(
    private val repository: Repository
) :
    ViewModelAssistedFactory<PageViewModel> {
    override fun create(): PageViewModel {
        return PageViewModel(repository)
    }
}
