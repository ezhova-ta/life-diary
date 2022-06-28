package com.example.lifediary.presentation.ui.main.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.MainNoteListSortMethod
import com.example.lifediary.domain.models.MainNote
import com.example.lifediary.domain.usecases.notes.ClearMainNoteListUseCase
import com.example.lifediary.domain.usecases.notes.GetMainNoteListSortMethodIdUseCase
import com.example.lifediary.domain.usecases.notes.GetSortedMainNoteListUseCase
import com.example.lifediary.domain.usecases.notes.SaveMainNoteListSortMethodIdUseCase
import com.example.lifediary.presentation.MainNoteListDropDownItemSortMethodMapper.toSortMethod
import com.example.lifediary.presentation.MainNoteListSortMethodDropDownItem
import com.example.lifediary.presentation.Text
import com.example.lifediary.presentation.navigation.Screens
import com.example.lifediary.presentation.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MainNotesViewModel : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var getMainNoteListSortMethodIdUseCase: GetMainNoteListSortMethodIdUseCase
	@Inject lateinit var clearMainNoteListUseCase: ClearMainNoteListUseCase
	@Inject lateinit var saveMainNoteListSortMethodIdUseCase: SaveMainNoteListSortMethodIdUseCase
	@Inject lateinit var getSortedMainNoteListUseCase: GetSortedMainNoteListUseCase

	val noteList by lazy { getSortedMainNoteListUseCase().asLiveData() }
	val isNotesVisible by lazy { noteList.map { it.isNotEmpty() } }
	val isNoteListSortMethodDropDownVisible by lazy { isNotesVisible }
	val noteListSortMethodId by lazy { getMainNoteListSortMethodIdUseCase().asLiveData() }

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
		router.navigateTo(Screens.getAddEditMainNoteScreen())
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
				clearMainNoteListUseCase()
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
		router.navigateTo(Screens.getAddEditMainNoteScreen(noteId))
	}

	fun onSortMethodSelected(dropDownItem: MainNoteListSortMethodDropDownItem) {
		val sortMethod = dropDownItem.toSortMethod()
		saveMainNoteListSortMethod(sortMethod)
	}

	private fun saveMainNoteListSortMethod(sortMethod: MainNoteListSortMethod) {
		CoroutineScope(Dispatchers.IO).launch {
			try {
				saveMainNoteListSortMethodIdUseCase(sortMethod.id)
			} catch(e: Exception) {
				showMessage(Text.TextResource(R.string.error))
			}
		}
	}

	override fun onCleared() {
		Toothpick.closeScope(DiScopes.MAIN_SCREEN_SCOPE)
		super.onCleared()
	}
}