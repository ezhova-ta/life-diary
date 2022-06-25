package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.repositories.DateNoteRepository
import javax.inject.Inject

class GetDateNoteUseCase @Inject constructor(
	private val noteRepository: DateNoteRepository
) {
	suspend operator fun invoke(day: Day): DateNote? {
		return noteRepository.getNote(day)
	}
}