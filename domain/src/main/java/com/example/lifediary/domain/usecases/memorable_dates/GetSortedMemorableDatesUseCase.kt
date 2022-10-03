package com.example.lifediary.domain.usecases.memorable_dates

import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import com.example.lifediary.domain.utils.sortBasedToday
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedMemorableDatesUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	operator fun invoke(): Flow<List<MemorableDate>> {
		return memorableDatesRepository.getFlowDates().map { it.sortBasedToday() }
	}
}