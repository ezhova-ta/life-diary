package com.example.lifediary.domain.usecases.memorable_dates

import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import javax.inject.Inject

class UpdateMemorableDateUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	suspend operator fun invoke(date: MemorableDate) {
		memorableDatesRepository.updateDate(date)
	}
}