package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.domain.repositories.WomanSectionRepository
import javax.inject.Inject

class ClearMenstruationPeriodListUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	suspend operator fun invoke() {
		womanSectionRepository.clearMenstruationPeriodList()
	}
}