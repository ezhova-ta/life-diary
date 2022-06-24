package com.example.lifediary.domain.usecases.woman_section

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.data.repositories.WomanSectionRepository
import javax.inject.Inject

class GetAllMenstruationPeriodsUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	operator fun invoke(): LiveData<List<MenstruationPeriod>> {
		return womanSectionRepository.getAllMenstruationPeriods()
	}
}