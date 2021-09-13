package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.SettingsLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val localDataSource: SettingsLocalDataSource
) {
    suspend fun setShoppingListSectionEnabled(isEnabled: Boolean) {
        localDataSource.setShoppingListSectionEnabled(isEnabled)
    }

    suspend fun setPostAddressesSectionEnabled(isEnabled: Boolean) {
        localDataSource.setPostAddressesSectionEnabled(isEnabled)
    }

    fun getShoppingListSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getShoppingListSectionEnabled()
    }

    fun getPostAddressesSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getPostAddressesSectionEnabled()
    }
}