package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.domain.repositories.WomanSectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDurationOfMenstruationPeriodUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	operator fun invoke(): Flow<Int> {
		return womanSectionRepository.getDurationOfMenstruationPeriod()
	}
}