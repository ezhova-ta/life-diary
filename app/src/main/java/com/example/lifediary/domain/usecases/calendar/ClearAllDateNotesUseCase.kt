package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.repositories.DateNoteRepository
import javax.inject.Inject

class ClearAllDateNotesUseCase @Inject constructor(
	private val dateNoteRepository: DateNoteRepository
) {
	suspend operator fun invoke() {
		dateNoteRepository.clearAllNotes()
	}
}