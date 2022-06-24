package com.example.lifediary.domain.usecases.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.domain.repositories.WomanSectionRepository
import com.example.lifediary.presentation.utils.dates.CalendarBuilder
import com.example.lifediary.presentation.utils.dates.getDaysBetween
import javax.inject.Inject

class GetDelayOfMenstruationUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository,
	private val getEstimatedNextMenstruationPeriodUseCase: GetEstimatedNextMenstruationPeriodUseCase
) {
	operator fun invoke(): LiveData<Long?> {
		return getEstimatedNextMenstruationPeriodUseCase().map { nextMenstruationPeriod ->
			val startNextMenstruationPeriod = nextMenstruationPeriod?.startDate ?: return@map null
			val now = CalendarBuilder().build()
			if(now.before(startNextMenstruationPeriod)) return@map null
			getDaysBetween(startNextMenstruationPeriod, now)
		}
	}
}