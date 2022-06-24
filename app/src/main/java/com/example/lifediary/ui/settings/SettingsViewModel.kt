package com.example.lifediary.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.R
import com.example.lifediary.data.domain.Text
import com.example.lifediary.data.repositories.DateNoteRepository
import com.example.lifediary.data.repositories.ToDoListRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.usecases.settings.*
import com.example.lifediary.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class SettingsViewModel : BaseViewModel() {
    @Inject lateinit var getShoppingListSectionEnabledUseCase: GetShoppingListSectionEnabledUseCase
    @Inject lateinit var getPostAddressesSectionEnabledUseCase: GetPostAddressesSectionEnabledUseCase
    @Inject lateinit var getMemorableDatesSectionEnabledUseCase: GetMemorableDatesSectionEnabledUseCase
    @Inject lateinit var getWomanSectionEnabledUseCase: GetWomanSectionEnabledUseCase
    @Inject lateinit var setShoppingListSectionEnabledUseCase: SetShoppingListSectionEnabledUseCase
    @Inject lateinit var setPostAddressesSectionEnabledUseCase: SetPostAddressesSectionEnabledUseCase
    @Inject lateinit var setMemorableDatesSectionEnabledUseCase: SetMemorableDatesSectionEnabledUseCase
    @Inject lateinit var setWomanSectionEnabledUseCase: SetWomanSectionEnabledUseCase

    @Inject lateinit var dateNoteRepository: DateNoteRepository
    @Inject lateinit var toDoListRepository: ToDoListRepository

    val isShoppingListSectionEnabled by lazy { getShoppingListSectionEnabledUseCase() }
    val isPostAddressesSectionEnabled by lazy { getPostAddressesSectionEnabledUseCase() }
    val isMemorableDatesSectionEnabled by lazy { getMemorableDatesSectionEnabledUseCase() }
    val isWomanSectionEnabled by lazy { getWomanSectionEnabledUseCase() }

    private val _showClearCalendarNotesConfirmationDialog = MutableLiveData(false)
    val showClearCalendarNotesConfirmationDialog: LiveData<Boolean>
        get() = _showClearCalendarNotesConfirmationDialog

    private val _showClearToDoListsConfirmationDialog = MutableLiveData(false)
    val showClearToDoListsConfirmationDialog: LiveData<Boolean>
        get() = _showClearToDoListsConfirmationDialog

    init {
        bindScope()
    }

    override fun bindScope() {
        val settingsScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.SETTINGS_SCOPE)
        Toothpick.inject(this, settingsScope)
    }

    fun onShoppingListSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isShoppingListSectionEnabled.value) return
        saveShoppingListSectionEnabled(isEnabled)
    }

    fun onPostAddressesSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isPostAddressesSectionEnabled.value) return
        savePostAddressesSectionEnabled(isEnabled)
    }

    fun onMemorableDatesSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isMemorableDatesSectionEnabled.value) return
        saveMemorableDatesSectionEnabled(isEnabled)
    }

    fun onWomenSectionEnabledChanged(isEnabled: Boolean) {
        if(isEnabled == isWomanSectionEnabled.value) return
        saveWomanSectionEnabled(isEnabled)
    }

    private fun saveShoppingListSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                setShoppingListSectionEnabledUseCase(isEnabled)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    private fun savePostAddressesSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                setPostAddressesSectionEnabledUseCase(isEnabled)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    private fun saveMemorableDatesSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                setMemorableDatesSectionEnabledUseCase(isEnabled)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error_try_again_later))
            }
        }
    }

    private fun saveWomanSectionEnabled(isEnabled: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                setWomanSectionEnabledUseCase(isEnabled)
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

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.SETTINGS_SCOPE)
        super.onCleared()
    }
}