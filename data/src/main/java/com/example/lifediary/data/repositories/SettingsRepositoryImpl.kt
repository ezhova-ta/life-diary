package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.SettingsLocalDataSource
import com.example.lifediary.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
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

    override fun getShoppingListSectionEnabled(): Flow<Boolean> {
        return localDataSource.getShoppingListSectionEnabled()
    }

    override fun getPostAddressesSectionEnabled(): Flow<Boolean> {
        return localDataSource.getPostAddressesSectionEnabled()
    }

    override fun getMemorableDatesSectionEnabled(): Flow<Boolean> {
        return localDataSource.getMemorableDatesSectionEnabled()
    }

    override fun getWomanSectionEnabled(): Flow<Boolean> {
        return localDataSource.getWomanSectionEnabled()
    }
}