package com.example.lifediary.ui.main.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.repositories.MainNotesRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainNotesViewModel : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var repository: MainNotesRepository
	val noteList: LiveData<List<MainNote>>
	val isNotesVisible: LiveData<Boolean>

	init {
		bindAppScope()
		noteList = repository.getNotes()
		isNotesVisible = noteList.map { it.isNotEmpty() }
	}

	fun onAddNoteClick() {
		router.navigateTo(Screens.getAddEditMainNoteFragment())
	}

	fun onClearNotesClick() {
		// TODO Clear confirmation dialog

		CoroutineScope(Dispatchers.IO).launch {
			try {
				repository.clearNoteList()
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_clear_list))
			}
		}
	}

	fun onEditNoteClick(note: MainNote) {
		val noteId = note.id ?: return
		router.navigateTo(Screens.getAddEditMainNoteFragment(noteId))
	}

	fun onDeleteNoteClick(note: MainNote) {
		val itemId = note.id ?: return

		CoroutineScope(Dispatchers.IO).launch {
			try {
				repository.deleteNote(itemId)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.deleting_item_error))
			}
		}
	}
}