package com.example.lifediary.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.R
import com.example.lifediary.data.repositories.DateNoteRepository
import com.example.lifediary.data.repositories.SettingsRepository
import com.example.lifediary.data.repositories.ToDoListRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel : BaseViewModel() {
    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var dateNoteRepository: DateNoteRepository
    @Inject lateinit var toDoListRepository: ToDoListRepository

    val isShoppingListSectionEnabled: LiveData<Boolean> by lazy {
        settingsRepository.getShoppingListSectionEnabled()
    }
    val isPostAddressesSectionEnabled: LiveData<Boolean> by lazy {
        settingsRepository.getPostAddressesSectionEnabled()
    }

    private val _showClearCalendarNotesConfirmationDialog = MutableLiveData(false)
    val showClearCalendarNotesConfirmationDialog: LiveData<Boolean>
        get() = _showClearCalendarNotesConfirmationDialog

    private val _showClearToDoListsConfirmationDialog = MutableLiveData(false)
    val showClearToDoListsConfirmationDialog: LiveData<Boolean>
        get() = _showClearToDoListsConfirmationDialog

    init {
        bindSettingsScope()
    }

    fun onShoppingListSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isShoppingListSectionEnabled.value) return
        saveShoppingListSectionEnabled(isEnabled)
    }

    fun onPostAddressesSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isPostAddressesSectionEnabled.value) return
        savePostAddressesSectionEnabled(isEnabled)
    }

    private fun saveShoppingListSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                settingsRepository.setShoppingListSectionEnabled(isEnabled)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    private fun savePostAddressesSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                settingsRepository.setPostAddressesSectionEnabled(isEnabled)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    fun onClearCalendarNotesClick() {
        _showClearCalendarNotesConfirmationDialog.value = true
    }

    fun onClearCalendarNotesConfirmed() {
        _showClearCalendarNotesConfirmationDialog.value = false
        clearCalendarNotes()
    }

    private fun clearCalendarNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                dateNoteRepository.clearNotes()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    fun onClearCalendarNotesCancelled() {
        _showClearCalendarNotesConfirmationDialog.value = false
    }

    fun onClearToDoListsClick() {
        _showClearToDoListsConfirmationDialog.value = true
    }

    fun onClearToDoListsConfirmed() {
        _showClearToDoListsConfirmationDialog.value = false
        clearToDoListsNotes()
    }

    private fun clearToDoListsNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                toDoListRepository.clearToDoLists()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    fun onClearToDoListsCancelled() {
        _showClearToDoListsConfirmationDialog.value = false
    }
}