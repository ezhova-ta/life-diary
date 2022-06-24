package com.example.lifediary.domain.usecases.notes

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.MainNote
import com.example.lifediary.data.repositories.MainNotesRepository
import com.example.lifediary.presentation.MainNoteListSorter
import com.example.lifediary.presentation.utils.livedata.TwoSourceLiveData
import javax.inject.Inject

class GetSortedMainNoteListUseCase @Inject constructor(
	private val notesRepository: MainNotesRepository
) {
	operator fun invoke(): LiveData<List<MainNote>> {
		return TwoSourceLiveData<List<MainNote>, Int?, List<MainNote>>(
			notesRepository.getNotes(),
			notesRepository.getSortMethodId()
		) { originalList, sortMethodId ->
			originalList ?: return@TwoSourceLiveData emptyList()
			val sorter = MainNoteListSorter.Factory.getInstance(sortMethodId)
			sorter.sort(originalList)
		}
	}
}