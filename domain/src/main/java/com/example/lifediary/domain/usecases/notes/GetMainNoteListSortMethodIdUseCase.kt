package com.example.lifediary.domain.usecases.notes

import com.example.lifediary.domain.repositories.MainNotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMainNoteListSortMethodIdUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	operator fun invoke(): Flow<Int?> {
		return notesRepository.getSortMethodId()
	}
}