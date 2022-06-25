package com.example.lifediary.domain.usecases.woman_section

import com.example.lifediary.domain.utils.CalendarBuilder
import com.example.lifediary.domain.utils.getDaysBetween
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDelayOfMenstruationUseCase @Inject constructor(
	private val getEstimatedNextMenstruationPeriodUseCase: GetEstimatedNextMenstruationPeriodUseCase
) {
	operator fun invoke(): Flow<Long?> {
		return getEstimatedNextMenstruationPeriodUseCase().map { nextMenstruationPeriod ->
			val startNextMenstruationPeriod = nextMenstruationPeriod?.startDate ?: return@map null
			val now = CalendarBuilder().build()
			if(now.before(startNextMenstruationPeriod)) return@map null
			getDaysBetween(startNextMenstruationPeriod, now)
		}
	}
}