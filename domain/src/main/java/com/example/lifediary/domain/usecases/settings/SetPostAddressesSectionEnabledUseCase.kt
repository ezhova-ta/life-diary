package com.example.lifediary.domain.usecases.settings

import com.example.lifediary.domain.repositories.SettingsRepository
import javax.inject.Inject

class SetPostAddressesSectionEnabledUseCase @Inject constructor(
	private val settingsRepository: SettingsRepository
) {
	suspend operator fun invoke(isEnabled: Boolean) {
		return settingsRepository.setPostAddressesSectionEnabled(isEnabled)
	}
}