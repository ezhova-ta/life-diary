package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.repositories.DateNoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDateNoteLiveDataUseCase @Inject constructor(
	private val noteRepository: DateNoteRepository
) {
	operator fun invoke(day: Day): Flow<DateNote?> {
		return noteRepository.getNoteLiveData(day)
	}
}