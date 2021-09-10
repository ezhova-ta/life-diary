package com.example.lifediary.ui.calendar.date.note

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.data.repositories.DateNoteRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditDateNoteViewModel(private val day: Day) : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var noteRepository: DateNoteRepository
	val isAddButtonVisible: LiveData<Boolean> by lazy { noteRepository.getNoteLiveData(day).map { it == null } }
	val noteText = MutableLiveData("")
	private var existingNote: DateNote? = null

	init {
		bindAppScope()
		substituteNoteTextInInput()
	}

	private fun substituteNoteTextInInput() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				existingNote = noteRepository.getNote(day)
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

		CoroutineScope(Dispatchers.IO).launch {
			try {
				val note = existingNote

				if(note == null) {
					noteRepository.addNote(text, day)
				} else {
					note.text = text
					noteRepository.updateNote(note)
				}

				router.exit()
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_save))
			}
		}
	}

	class Factory(private val day: Day) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return AddEditDateNoteViewModel(day) as T
		}
	}
}