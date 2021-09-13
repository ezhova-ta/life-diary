package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.SettingsLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val localDataSource: SettingsLocalDataSource
) {
    fun setShoppingListSectionEnabled(isEnabled: Boolean) {
        localDataSource.setShoppingListSectionEnabled(isEnabled)
    }

    fun setPostAddressesSectionEnabled(isEnabled: Boolean) {
        localDataSource.setPostAddressesSectionEnabled(isEnabled)
    }

    fun getShoppingListSectionEnabled(): Boolean {
        return localDataSource.getShoppingListSectionEnabled()
    }

    fun getPostAddressesSectionEnabled(): Boolean {
        return localDataSource.getPostAddressesSectionEnabled()
    }
}