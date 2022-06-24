package com.example.lifediary.domain.usecases.memorable_dates

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.data.repositories.MemorableDatesRepository
import com.example.lifediary.presentation.utils.sortBasedToday
import javax.inject.Inject

class GetSortedMemorableDatesUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	operator fun invoke(): LiveData<List<MemorableDate>> {
		return memorableDatesRepository.getDates().map { it.sortBasedToday() }
	}
}