package com.example.lifediary.domain.usecases.notes

import com.example.lifediary.domain.models.MainNote
import com.example.lifediary.domain.repositories.MainNotesRepository
import com.example.lifediary.domain.utils.sorters.MainNoteListSorter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSortedMainNoteListUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	operator fun invoke(): Flow<List<MainNote>> {
		val originalMainNoteListFlow = notesRepository.getNotes()
		val sortMethodIdFlow = notesRepository.getSortMethodId()

		return originalMainNoteListFlow.combine(sortMethodIdFlow) { originalMainNoteList, sortMethodId ->
			val sorter = MainNoteListSorter.Factory.getInstance(sortMethodId)
			sorter.sort(originalMainNoteList)
		}
	}
}