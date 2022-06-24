package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.SettingsLocalDataSource
import com.example.lifediary.domain.repositories.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val localDataSource: SettingsLocalDataSource
) : SettingsRepository {
    override suspend fun setShoppingListSectionEnabled(isEnabled: Boolean) {
        localDataSource.setShoppingListSectionEnabled(isEnabled)
    }

    override suspend fun setPostAddressesSectionEnabled(isEnabled: Boolean) {
        localDataSource.setPostAddressesSectionEnabled(isEnabled)
    }

    override suspend fun setMemorableDatesSectionEnabled(isEnabled: Boolean) {
        localDataSource.setMemorableDatesSectionEnabled(isEnabled)
    }

    override suspend fun setWomanSectionEnabled(isEnabled: Boolean) {
        localDataSource.setWomanSectionEnabled(isEnabled)
    }

    override fun getShoppingListSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getShoppingListSectionEnabled()
    }

    override fun getPostAddressesSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getPostAddressesSectionEnabled()
    }

    override fun getMemorableDatesSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getMemorableDatesSectionEnabled()
    }

    override fun getWomanSectionEnabled(): LiveData<Boolean> {
        return localDataSource.getWomanSectionEnabled()
    }
}