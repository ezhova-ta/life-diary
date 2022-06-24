package com.example.lifediary.domain.usecases.settings

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.repositories.SettingsRepository
import javax.inject.Inject

class GetWomanSectionEnabledUseCase @Inject constructor(
	private val settingsRepository: SettingsRepository
) {
	operator fun invoke(): LiveData<Boolean> {
		return settingsRepository.getWomanSectionEnabled()
	}
}