package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMemorableDatesForDayUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	operator fun invoke(day: Day): Flow<List<MemorableDate>> {
		return memorableDatesRepository.getFlowDates(day)
	}
}