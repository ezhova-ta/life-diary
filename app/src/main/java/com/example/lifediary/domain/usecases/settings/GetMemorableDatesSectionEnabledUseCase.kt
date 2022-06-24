package com.example.lifediary.domain.usecases.settings

import androidx.lifecycle.LiveData
import com.example.lifediary.data.repositories.SettingsRepository
import javax.inject.Inject

class GetMemorableDatesSectionEnabledUseCase @Inject constructor(
	private val settingsRepository: SettingsRepository
) {
	operator fun invoke(): LiveData<Boolean> {
		return settingsRepository.getMemorableDatesSectionEnabled()
	}
}