package com.example.lifediary.domain.usecases.woman_section

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.data.repositories.WomanSectionRepository
import com.example.lifediary.presentation.utils.dates.plusDays
import com.example.lifediary.presentation.utils.livedata.ThreeSourceLiveData
import javax.inject.Inject

class GetEstimatedNextMenstruationPeriodUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository,
	private val getLastMenstruationPeriodUseCase: GetLastMenstruationPeriodUseCase
) {
	operator fun invoke(): LiveData<MenstruationPeriod?> {
		return ThreeSourceLiveData(
			getLastMenstruationPeriodUseCase(),
			womanSectionRepository.getDurationOfMenstrualCycle(),
			womanSectionRepository.getDurationOfMenstruationPeriod()
		) { lastMenstruationPeriod, durationOfMenstrualCycle, durationOfMenstruationPeriod ->
			lastMenstruationPeriod ?: return@ThreeSourceLiveData null
			durationOfMenstrualCycle ?: return@ThreeSourceLiveData null
			durationOfMenstruationPeriod ?: return@ThreeSourceLiveData null

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