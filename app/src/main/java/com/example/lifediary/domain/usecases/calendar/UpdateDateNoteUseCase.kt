package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.data.domain.Day
import com.example.lifediary.data.repositories.DateNoteRepository
import javax.inject.Inject

class UpdateDateNoteUseCase @Inject constructor(
	private val noteRepository: DateNoteRepository
) {
	suspend operator fun invoke(note: DateNote) {
		noteRepository.updateNote(note)
	}
}