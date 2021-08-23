package com.example.lifediary.ui.notes

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.data.repositories.NotesRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditNotesViewModel(private val day: Day) : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var notesRepository: NotesRepository

	private var existingNotes: Notes? = null
	val notesText = MutableLiveData("")
	val isAddButtonVisible: LiveData<Boolean>
	val isSaveButtonVisible: LiveData<Boolean>

	init {
		bindAppScope()
		val existingNotesLiveData = notesRepository.getNotesLiveData(day)
		isAddButtonVisible = existingNotesLiveData.map { it == null }
		isSaveButtonVisible = existingNotesLiveData.map { it != null }

		viewModelScope.launch(Dispatchers.IO) {
			try {
				existingNotes = notesRepository.getNotes(day)
				existingNotes?.text?.let { notesText.postValue(it) }
			} catch(e: Exception) {
				val messageRes = R.string.error
				popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
			}
		}
	}

	fun onSaveNotesClick() {
		val text = notesText.value?.trim()

		if(text.isNullOrBlank()) {
			notesText.value = ""
			return
		}

		CoroutineScope(Dispatchers.IO).launch {
			try {
				val notes = existingNotes

				if(notes == null) {
					notesRepository.addNotes(text, day)
				} else {
					notes.text = text
					notesRepository.updateNotes(notes)
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
			return AddEditNotesViewModel(day) as T
		}
	}
}