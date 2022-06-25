package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.domain.repositories.WomanSectionRepository
import javax.inject.Inject

class AddMenstruationPeriodUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	suspend operator fun invoke(period: MenstruationPeriod) {
		womanSectionRepository.addMenstruationPeriod(period)
	}
}