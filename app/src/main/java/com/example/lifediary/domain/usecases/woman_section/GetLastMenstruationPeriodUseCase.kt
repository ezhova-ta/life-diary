package com.example.lifediary.domain.usecases.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.data.repositories.WomanSectionRepository
import javax.inject.Inject

class GetLastMenstruationPeriodUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository,
	private val getAllMenstruationPeriodsUseCase: GetAllMenstruationPeriodsUseCase
) {
	operator fun invoke(): LiveData<MenstruationPeriod?> {
		return getAllMenstruationPeriodsUseCase().map {
			it.maxByOrNull { period -> period.startDate }
		}
	}
}