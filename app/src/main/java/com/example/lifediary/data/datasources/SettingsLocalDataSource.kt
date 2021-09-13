package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lifediary.data.SettingsDataStoreManager
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val settingsDataStoreManager: SettingsDataStoreManager
) {
    suspend fun setShoppingListSectionEnabled(isEnabled: Boolean) {
        settingsDataStoreManager.setShoppingListSectionEnabled(isEnabled)
    }

    suspend fun setPostAddressesSectionEnabled(isEnabled: Boolean) {
        settingsDataStoreManager.setPostAddressesSectionEnabled(isEnabled)
    }

    fun getShoppingListSectionEnabled(): LiveData<Boolean> {
        return settingsDataStoreManager.isShoppingListSectionEnabled.asLiveData()
    }

    fun getPostAddressesSectionEnabled(): LiveData<Boolean> {
        return settingsDataStoreManager.isPostAddressesSectionEnabled.asLiveData()
    }
}