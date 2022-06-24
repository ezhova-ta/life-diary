package com.example.lifediary.domain.usecases.settings

import com.example.lifediary.data.repositories.SettingsRepository
import javax.inject.Inject

class SetWomanSectionEnabledUseCase @Inject constructor(
	private val settingsRepository: SettingsRepository
) {
	suspend operator fun invoke(isEnabled: Boolean) {
		settingsRepository.setWomanSectionEnabled(isEnabled)
	}
}