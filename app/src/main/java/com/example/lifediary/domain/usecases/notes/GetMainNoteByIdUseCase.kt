package com.example.lifediary.domain.usecases.notes

import com.example.lifediary.domain.models.MainNote
import com.example.lifediary.data.repositories.MainNotesRepository
import javax.inject.Inject

class GetMainNoteByIdUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	suspend operator fun invoke(id: Long): MainNote? {
		return notesRepository.getNote(id)
	}
}