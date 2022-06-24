package com.example.lifediary.domain.usecases.settings

import com.example.lifediary.data.repositories.SettingsRepository
import javax.inject.Inject

class SetMemorableDatesSectionEnabledUseCase @Inject constructor(
	private val settingsRepository: SettingsRepository
) {
	suspend operator fun invoke(isEnabled: Boolean) {
		return settingsRepository.setMemorableDatesSectionEnabled(isEnabled)
	}
}