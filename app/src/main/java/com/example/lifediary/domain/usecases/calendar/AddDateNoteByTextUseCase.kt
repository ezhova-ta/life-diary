package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.Day
import com.example.lifediary.data.repositories.DateNoteRepository
import javax.inject.Inject

class AddDateNoteByTextUseCase @Inject constructor(
	private val noteRepository: DateNoteRepository
) {
	suspend operator fun invoke(text: String, day: Day) {
		noteRepository.addNote(text, day)
	}
}