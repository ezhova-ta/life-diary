package com.example.lifediary.data.datasources

import com.example.lifediary.data.SettingsDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    fun getShoppingListSectionEnabled(): Flow<Boolean> {
        return settingsDataStoreManager.isShoppingListSectionEnabled
    }

    fun getPostAddressesSectionEnabled(): Flow<Boolean> {
        return settingsDataStoreManager.isPostAddressesSectionEnabled
    }

    fun getMemorableDatesSectionEnabled(): Flow<Boolean> {
        return settingsDataStoreManager.isMemorableDatesSectionEnabled
    }

    fun getWomanSectionEnabled(): Flow<Boolean> {
        return settingsDataStoreManager.isWomanSectionEnabled
    }
}