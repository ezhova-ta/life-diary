package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData

interface SettingsRepository {
	suspend fun setShoppingListSectionEnabled(isEnabled: Boolean)
	suspend fun setPostAddressesSectionEnabled(isEnabled: Boolean)
	suspend fun setMemorableDatesSectionEnabled(isEnabled: Boolean)
	suspend fun setWomanSectionEnabled(isEnabled: Boolean)
	fun getShoppingListSectionEnabled(): LiveData<Boolean>
	fun getPostAddressesSectionEnabled(): LiveData<Boolean>
	fun getMemorableDatesSectionEnabled(): LiveData<Boolean>
	fun getWomanSectionEnabled(): LiveData<Boolean>
}