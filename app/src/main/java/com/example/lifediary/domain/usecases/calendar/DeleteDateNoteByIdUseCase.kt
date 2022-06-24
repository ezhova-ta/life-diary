package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.repositories.DateNoteRepository
import javax.inject.Inject

class DeleteDateNoteByIdUseCase @Inject constructor(
	private val noteRepository: DateNoteRepository
) {
	suspend operator fun invoke(id: Long) {
		noteRepository.deleteNote(id)
	}
}