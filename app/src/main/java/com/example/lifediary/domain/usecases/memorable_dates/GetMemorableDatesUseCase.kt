package com.example.lifediary.domain.usecases.memorable_dates

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.repositories.MemorableDatesRepository
import javax.inject.Inject

class GetMemorableDatesUseCase @Inject constructor(
	private val memorableDatesRepository: MemorableDatesRepository
) {
	operator fun invoke(): LiveData<List<MemorableDate>> {
		return memorableDatesRepository.getDates()
	}
}