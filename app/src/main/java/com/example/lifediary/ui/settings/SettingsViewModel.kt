package com.example.lifediary.ui.settings

import androidx.lifecycle.LiveData
import com.example.lifediary.data.repositories.SettingsRepository
import com.example.lifediary.ui.BaseViewModel
import javax.inject.Inject

class SettingsViewModel : BaseViewModel() {
    @Inject
    lateinit var settingsRepository: SettingsRepository
    val isShoppingListSectionEnabled: LiveData<Boolean> by lazy {
        settingsRepository.getShoppingListSectionEnabled()
    }
    val isPostAddressesSectionEnabled: LiveData<Boolean> by lazy {
        settingsRepository.getPostAddressesSectionEnabled()
    }

    init {
        bindSettingsScope()
    }
}