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

    suspend fun setMemorableDatesSectionEnabled(isEnabled: Boolean) {
        localDataSource.setMemorableDatesSectionEnabled(isEnabled)
    }

    suspend fun setWomanSectionEnabled(isEnabled: Boolean) {
        localDataSource.setWomanSectionEnabled(isEnabled)
    }

    fun getShoppingListSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getShoppingListSectionEnabled()
    }

    fun getPostAddressesSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getPostAddressesSectionEnabled()
    }

    fun getMemorableDatesSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getMemorableDatesSectionEnabled()
    }

    fun getWomanSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getWomanSectionEnabled()
    }
}