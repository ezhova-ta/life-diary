package com.example.lifediary.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
	suspend fun setShoppingListSectionEnabled(isEnabled: Boolean)
	suspend fun setPostAddressesSectionEnabled(isEnabled: Boolean)
	suspend fun setMemorableDatesSectionEnabled(isEnabled: Boolean)
	suspend fun setWomanSectionEnabled(isEnabled: Boolean)
	fun getShoppingListSectionEnabled(): Flow<Boolean>
	fun getPostAddressesSectionEnabled(): Flow<Boolean>
	fun getMemorableDatesSectionEnabled(): Flow<Boolean>
	fun getWomanSectionEnabled(): Flow<Boolean>
}