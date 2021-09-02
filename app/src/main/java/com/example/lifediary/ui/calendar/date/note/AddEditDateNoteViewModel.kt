package com.example.lifediary.ui.calendar.date.note

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.Note
import com.example.lifediary.data.repositories.NoteRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditDateNoteViewModel(private val day: Day) : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var noteRepository: NoteRepository

	private var existingNote: Note? = null
	val noteText = MutableLiveData("")
	val isAddButtonVisible: LiveData<Boolean>

	init {
		bindAppScope()
		isAddButtonVisible = noteRepository.getNoteLiveData(day).map { it == null }

		viewModelScope.launch(Dispatchers.IO) {
			try {
				existingNote = noteRepository.getNote(day)
				existingNote?.text?.let { noteText.postValue(it) }
			} catch(e: Exception) {
				val messageRes = R.string.error
				popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
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
				val messageRes = R.string.failed_to_save
				popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
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