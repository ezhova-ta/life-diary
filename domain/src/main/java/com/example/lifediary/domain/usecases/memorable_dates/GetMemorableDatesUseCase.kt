package com.example.lifediary.domain.usecases.memorable_dates

import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMemorableDatesUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	operator fun invoke(): Flow<List<MemorableDate>> {
		return memorableDatesRepository.getFlowDates()
	}
}