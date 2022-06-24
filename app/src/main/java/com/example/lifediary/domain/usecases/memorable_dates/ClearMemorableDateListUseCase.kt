package com.example.lifediary.domain.usecases.memorable_dates

import com.example.lifediary.domain.repositories.MemorableDatesRepository
import javax.inject.Inject

class ClearMemorableDateListUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	suspend operator fun invoke() {
		memorableDatesRepository.clearDates()
	}
}