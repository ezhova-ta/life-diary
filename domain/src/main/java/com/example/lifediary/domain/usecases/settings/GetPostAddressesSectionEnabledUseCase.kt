package com.example.lifediary.domain.usecases.settings

import com.example.lifediary.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostAddressesSectionEnabledUseCase @Inject constructor(
	private val settingsRepository: SettingsRepository
) {
	operator fun invoke(): Flow<Boolean> {
		return settingsRepository.getPostAddressesSectionEnabled()
	}
}