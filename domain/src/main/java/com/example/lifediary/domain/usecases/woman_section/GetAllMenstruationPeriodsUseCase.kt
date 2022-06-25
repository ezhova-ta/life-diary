package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.domain.repositories.WomanSectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMenstruationPeriodsUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	operator fun invoke(): Flow<List<MenstruationPeriod>> {
		return womanSectionRepository.getAllMenstruationPeriods()
	}
}