package com.example.lifediary.domain.usecases.notes

import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.repositories.MainNotesRepository
import javax.inject.Inject

class GetMainNoteFromIdUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	suspend operator fun invoke(id: Long): MainNote? {
		return notesRepository.getNote(id)
	}
}