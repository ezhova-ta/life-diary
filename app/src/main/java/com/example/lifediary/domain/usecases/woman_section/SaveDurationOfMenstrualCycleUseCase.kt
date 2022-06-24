package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.data.repositories.WomanSectionRepository
import javax.inject.Inject

class SaveDurationOfMenstrualCycleUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	suspend operator fun invoke(duration: Int) {
		womanSectionRepository.setDurationOfMenstrualCycle(duration)
	}
}