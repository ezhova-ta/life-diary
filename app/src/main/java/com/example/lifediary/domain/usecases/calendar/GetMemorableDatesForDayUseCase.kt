package com.example.lifediary.domain.usecases.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.data.domain.Day
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.repositories.MemorableDatesRepository
import javax.inject.Inject

class GetMemorableDatesForDayUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	operator fun invoke(day: Day): LiveData<List<MemorableDate>> {
		return memorableDatesRepository.getDates(day)
	}
}