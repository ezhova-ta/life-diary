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

    suspend fun setMemorableDatesSectionEnabled(isEnabled: Boolean) {
        settingsDataStoreManager.setMemorableDatesSectionEnabled(isEnabled)
    }

    suspend fun setWomanSectionEnabled(isEnabled: Boolean) {
        settingsDataStoreManager.setWomanSectionEnabled(isEnabled)
    }

    fun getShoppingListSectionEnabled(): LiveData<Boolean> {
        return settingsDataStoreManager.isShoppingListSectionEnabled.asLiveData()
    }

    fun getPostAddressesSectionEnabled(): LiveData<Boolean> {
        return settingsDataStoreManager.isPostAddressesSectionEnabled.asLiveData()
    }

    fun getMemorableDatesSectionEnabled(): LiveData<Boolean> {
        return settingsDataStoreManager.isMemorableDatesSectionEnabled.asLiveData()
    }

    fun getWomanSectionEnabled(): LiveData<Boolean> {
        return settingsDataStoreManager.isWomanSectionEnabled.asLiveData()
    }
}