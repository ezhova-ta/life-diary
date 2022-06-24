package com.example.lifediary.domain.usecases.notes

import com.example.lifediary.data.repositories.MainNotesRepository
import javax.inject.Inject

class ClearMainNoteListUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	suspend operator fun invoke() {
		notesRepository.clearNotes()
	}
}