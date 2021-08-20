package com.example.lifediary.ui.notes

import androidx.lifecycle.MutableLiveData
import com.example.lifediary.R
import com.example.lifediary.data.repositories.NotesRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.OneTimeEvent
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddNotesViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var notesRepository: NotesRepository

	val notesText = MutableLiveData("")

	init {
		bindAppScope()
	}

	fun onSaveClick() {
//		CoroutineScope(Dispatchers.IO).launch {
//			try {
//				notesRepository.saveNotes(testNotesText)
//			} catch(e: Exception) {
//				val messageRes = R.string.failed_to_save
//				popupMessageEvent.postValue(OneTimeEvent(Text.TextResource(messageRes)))
//			}
//		}
	}
}