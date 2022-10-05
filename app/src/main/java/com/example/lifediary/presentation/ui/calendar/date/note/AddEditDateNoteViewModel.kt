package com.example.lifediary.presentation.ui.calendar.date.note

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes.ADD_EDIT_DATE_NOTE_VIEW_MODEL_SCOPE
import com.example.lifediary.di.DiScopes.APP_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_ACTIVITY_VIEW_MODEL_SCOPE
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.usecases.calendar.AddDateNoteByTextUseCase
import com.example.lifediary.domain.usecases.calendar.GetDateNoteLiveDataUseCase
import com.example.lifediary.domain.usecases.calendar.GetDateNoteUseCase
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.presentation.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class AddEditDateNoteViewModel(private val day: Day) : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var getDateNoteLiveDataUseCase: GetDateNoteLiveDataUseCase
	@Inject lateinit var getDateNoteUseCase: GetDateNoteUseCase
	@Inject lateinit var addDateNoteByTextUseCase: AddDateNoteByTextUseCase

	val isAddButtonVisible by lazy { getDateNoteLiveDataUseCase(day).map { it == null }.asLiveData() }
	val noteText = MutableLiveData("")
	private var existingNote: DateNote? = null

	private val _inputNeedsFocus = MutableLiveData(true)
	val inputNeedsFocus: LiveData<Boolean> get() = _inputNeedsFocus

	init {
		bindScope()
		substituteNoteTextInInput()
	}

	override fun bindScope() {
		val calendarDateScope = Toothpick.openScopes(
			APP_SCOPE,
			MAIN_ACTIVITY_VIEW_MODEL_SCOPE,
			ADD_EDIT_DATE_NOTE_VIEW_MODEL_SCOPE
		)
		Toothpick.inject(this, calendarDateScope)
	}

	private fun substituteNoteTextInInput() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				existingNote = getDateNoteUseCase(day)
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
		} else {
			_inputNeedsFocus.value = false
			saveNote(text)
		}
	}

	private fun saveNote(text: String) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				addDateNoteByTextUseCase(text, day)
				router.exit()
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.failed_to_save))
			}
		}
	}

	override fun onCleared() {
		Toothpick.closeScope(ADD_EDIT_DATE_NOTE_VIEW_MODEL_SCOPE)
		super.onCleared()
	}

	class Factory(private val day: Day) : ViewModelProvider.Factory {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel?> create(modelClass: Class<T>): T {
			return AddEditDateNoteViewModel(day) as T
		}
	}
}