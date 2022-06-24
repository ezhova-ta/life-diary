package com.example.lifediary.domain.usecases.notes

import androidx.lifecycle.LiveData
import com.example.lifediary.data.repositories.MainNotesRepository
import javax.inject.Inject

class GetMainNoteListSortMethodIdUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	operator fun invoke(): LiveData<Int?> {
		return notesRepository.getSortMethodId()
	}
}