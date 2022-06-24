package com.example.lifediary.domain.usecases.notes

import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.repositories.MainNotesRepository
import javax.inject.Inject

class UpdateMainNoteUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	suspend operator fun invoke(note: MainNote) {
		notesRepository.updateNote(note)
	}
}