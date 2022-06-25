package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.domain.models.MenstruationPeriod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLastMenstruationPeriodUseCase @Inject constructor(
	private val getAllMenstruationPeriodsUseCase: GetAllMenstruationPeriodsUseCase
) {
	operator fun invoke(): Flow<MenstruationPeriod?> {
		return getAllMenstruationPeriodsUseCase().map {
			it.maxByOrNull { period -> period.startDate }
		}
	}
}