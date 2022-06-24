package com.example.lifediary.domain.usecases.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import com.example.lifediary.data.repositories.DateNoteRepository
import javax.inject.Inject

class GetDateNoteLiveDataUseCase @Inject constructor(
	private val noteRepository: DateNoteRepository
) {
	operator fun invoke(day: Day): LiveData<DateNote?> {
		return noteRepository.getNoteLiveData(day)
	}
}