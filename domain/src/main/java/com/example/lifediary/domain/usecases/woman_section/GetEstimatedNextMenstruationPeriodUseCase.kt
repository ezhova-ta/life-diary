package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.domain.repositories.WomanSectionRepository
import com.example.lifediary.domain.utils.plusDays
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetEstimatedNextMenstruationPeriodUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository,
	private val getLastMenstruationPeriodUseCase: GetLastMenstruationPeriodUseCase
) {
	operator fun invoke(): Flow<MenstruationPeriod?> {
		return combine(
			getLastMenstruationPeriodUseCase(),
			womanSectionRepository.getDurationOfMenstrualCycleFlow(),
			womanSectionRepository.getDurationOfMenstruationPeriodFlow()
		) { lastMenstruationPeriod, durationOfMenstrualCycle, durationOfMenstruationPeriod ->
			lastMenstruationPeriod ?: return@combine null

			val startDateOfNextMenstruationPeriod = lastMenstruationPeriod.startDate.plusDays(
				durationOfMenstrualCycle
			)
			val endDateOfNextMenstruationPeriod = startDateOfNextMenstruationPeriod.plusDays(
				durationOfMenstruationPeriod - 1
			)

			MenstruationPeriod(
				startDate = startDateOfNextMenstruationPeriod,
				endDate = endDateOfNextMenstruationPeriod
			)
		}
	}
}