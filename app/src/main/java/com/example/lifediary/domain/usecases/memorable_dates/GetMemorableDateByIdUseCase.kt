package com.example.lifediary.domain.usecases.memorable_dates

import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.data.repositories.MemorableDatesRepository
import javax.inject.Inject

class GetMemorableDateByIdUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	suspend operator fun invoke(id: Long): MemorableDate? {
		return memorableDatesRepository.getDate(id)
	}
}