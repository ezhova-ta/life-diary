package com.example.lifediary.ui.main.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.repositories.MainNotesRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MainNotesViewModel : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var notesRepository: MainNotesRepository
	val noteList: LiveData<List<MainNote>> by lazy { notesRepository.getNotes() }
	val isNotesVisible: LiveData<Boolean> by lazy { noteList.map { it.isNotEmpty() } }

	private val _showClearNoteListConfirmationDialog = MutableLiveData(false)
	val showClearNoteListConfirmationDialog: LiveData<Boolean>
		get() = _showClearNoteListConfirmationDialog

	init {
		bindScope()
	}

	override fun bindScope() {
		val mainScreenScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.MAIN_SCREEN_SCOPE)
		Toothpick.inject(this, mainScreenScope)
	}

	fun onAddNoteClick() {
		router.navigateTo(Screens.getAddEditMainNoteFragment())
	}

	fun onClearNotesClick() {
		if(noteList.value.isNullOrEmpty()) return
		_showClearNoteListConfirmationDialog.value = true
	}

	fun onClearNoteListConfirmed() {
		_showClearNoteListConfirmationDialog.value = false
		clearNoteList()
	}

	private fun clearNoteList() {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				notesRepository.clearNotes()
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_clear_list))
			}
		}
	}

	fun onClearNoteListCancelled() {
		_showClearNoteListConfirmationDialog.value = false
	}

	fun onNoteLongClick(note: MainNote) {
		val noteId = note.id ?: return
		router.navigateTo(Screens.getAddEditMainNoteFragment(noteId))
	}
}