package com.example.lifediary.ui.settings

import androidx.lifecycle.LiveData
import com.example.lifediary.R
import com.example.lifediary.data.repositories.SettingsRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun onShoppingListSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isShoppingListSectionEnabled.value) return
        saveShoppingListSectionEnabled(isEnabled)
    }

    fun onPostAddressesSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isPostAddressesSectionEnabled.value) return
        savePostAddressesSectionEnabled(isEnabled)
    }

    private fun saveShoppingListSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                settingsRepository.setShoppingListSectionEnabled(isEnabled)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    private fun savePostAddressesSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                settingsRepository.setPostAddressesSectionEnabled(isEnabled)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }
}