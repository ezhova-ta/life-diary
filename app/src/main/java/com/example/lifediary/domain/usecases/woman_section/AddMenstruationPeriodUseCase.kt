package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.data.domain.MenstruationPeriod
import com.example.lifediary.data.repositories.WomanSectionRepository
import javax.inject.Inject

class AddMenstruationPeriodUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	suspend operator fun invoke(period: MenstruationPeriod) {
		womanSectionRepository.addMenstruationPeriod(period)
	}
}