package com.example.lifediary.domain.usecases.woman_section

import androidx.lifecycle.LiveData
import com.example.lifediary.data.repositories.WomanSectionRepository
import javax.inject.Inject

class GetDurationOfMenstruationPeriodUseCase @Inject constructor(
	private val womanSectionRepository: WomanSectionRepository
) {
	operator fun invoke(): LiveData<Int> {
		return womanSectionRepository.getDurationOfMenstruationPeriod()
	}
}