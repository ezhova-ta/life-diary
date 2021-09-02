package com.example.lifediary.ui.main.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.repositories.MainNoteRepository
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainNotesViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var repository: MainNoteRepository

	val noteList: LiveData<List<MainNote>>
	val isNotesVisible: LiveData<Boolean>

	init {
		bindAppScope()
		noteList = repository.getNotes()
		isNotesVisible = noteList.map { it.isNotEmpty() }
	}

	fun onAddNoteClick() {
		TODO()
	}

	fun onClearNotesClick() {
		TODO()
	}

	fun onEditNoteClick(note: MainNote) {
		TODO()
	}

	fun onDeleteNoteClick(note: MainNote) {
		TODO()
	}
}