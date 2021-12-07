package com.example.lifediary.ui.main.notes

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.repositories.MainNotesRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class AddEditMainNoteViewModel(private val noteId: Long? = null) : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var notesRepository: MainNotesRepository
	val noteText = MutableLiveData("")
	val isAddButtonVisible = noteId == null
	private var existingNote: MainNote? = null

	private val _inputNeedsFocus = MutableLiveData(true)
	val inputNeedsFocus: LiveData<Boolean>
		get() = _inputNeedsFocus

	init {
		bindScope()
		substituteNoteTextInInput()
	}

	override fun bindScope() {
		val mainScreenScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.MAIN_SCREEN_SCOPE)
		Toothpick.inject(this, mainScreenScope)
	}

	private fun substituteNoteTextInInput() {
		if(noteId == null) return

		viewModelScope.launch(Dispatchers.IO) {
			try {
				existingNote = notesRepository.getNote(noteId)
				existingNote?.text?.let { noteText.postValue(it) }
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error))
			}
		}
	}

	fun onSaveNoteClick() {
		val text = noteText.value?.trim()

		if(text.isNullOrBlank()) {
			noteText.value = ""
			return
		}

		_inputNeedsFocus.value = false
		saveNote(text)
	}

	private fun saveNote(text: String) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				val note = existingNote

				if(note == null) {
					notesRepository.addNote(text)
				} else {
					note.text = text
					notesRepository.updateNote(note)
				}

				router.exit()
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_save))
			}
		}
	}

	class Factory(private val noteId: Long?) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return AddEditMainNoteViewModel(noteId) as T
		}
	}
}