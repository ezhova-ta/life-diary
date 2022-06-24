package com.example.lifediary.ui.main.notes

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.domain.Text
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.usecases.notes.AddMainNoteFromTextUseCase
import com.example.lifediary.domain.usecases.notes.DeleteMainNoteByIdUseCase
import com.example.lifediary.domain.usecases.notes.GetMainNoteByIdUseCase
import com.example.lifediary.domain.usecases.notes.UpdateMainNoteUseCase
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class AddEditMainNoteViewModel(private val noteId: Long? = null) : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var getMainNoteByIdUseCase: GetMainNoteByIdUseCase
	@Inject lateinit var addMainNoteFromTextUseCase: AddMainNoteFromTextUseCase
	@Inject lateinit var updateMainNoteUseCase: UpdateMainNoteUseCase
	@Inject lateinit var deleteMainNoteByIdUseCase: DeleteMainNoteByIdUseCase

	val noteText = MutableLiveData("")
	val isAddButtonVisible = noteId == null
	private var existingNote: MainNote? = null

	private val _inputNeedsFocus = MutableLiveData(true)
	val inputNeedsFocus: LiveData<Boolean> get() = _inputNeedsFocus

	private val _showDeleteNoteConfirmationDialog = MutableLiveData<Long?>(null)
	val showDeleteNoteConfirmationDialog: LiveData<Long?>
		get() = _showDeleteNoteConfirmationDialog

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
				existingNote = getMainNoteByIdUseCase(noteId)
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
					addMainNoteFromTextUseCase(text)
				} else {
					note.text = text
					updateMainNoteUseCase(note)
				}

				router.exit()
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_save))
			}
		}
	}

	fun onDeleteNoteClick() {
		_showDeleteNoteConfirmationDialog.value = noteId
	}

	fun onDeleteNoteConfirmed(noteId: Long) {
		_showDeleteNoteConfirmationDialog.value = null
		deleteNote(noteId)
	}

	private fun deleteNote(noteId: Long) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				deleteMainNoteByIdUseCase(noteId)
				router.exit()
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.deleting_item_error))
			}
		}
	}

	fun onDeleteNoteCancelled() {
		_showDeleteNoteConfirmationDialog.value = null
	}

	override fun onCleared() {
		Toothpick.closeScope(DiScopes.MAIN_SCREEN_SCOPE)
		super.onCleared()
	}

	class Factory(private val noteId: Long?) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return AddEditMainNoteViewModel(noteId) as T
		}
	}
}