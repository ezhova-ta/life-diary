package com.example.lifediary.ui.memorable_dates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.domain.Text
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.usecases.memorable_dates.ClearMemorableDateListUseCase
import com.example.lifediary.domain.usecases.memorable_dates.DeleteMemorableDateFromIdUseCase
import com.example.lifediary.domain.usecases.memorable_dates.GetSortedMemorableDatesUseCase
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MemorableDatesViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var clearMemorableDateListUseCase: ClearMemorableDateListUseCase
    @Inject lateinit var deleteMemorableDateFromIdUseCase: DeleteMemorableDateFromIdUseCase
    @Inject lateinit var getSortedMemorableDatesUseCase: GetSortedMemorableDatesUseCase

    val dates by lazy { getSortedMemorableDatesUseCase() }
    val isDatesVisible by lazy { dates.map { it.isNotEmpty() } }

    private val _showClearDateListConfirmationDialog = MutableLiveData(false)
    val showClearDateListConfirmationDialog: LiveData<Boolean>
        get() = _showClearDateListConfirmationDialog

    private val _showDeleteDateConfirmationDialog = MutableLiveData<Long?>(null)
    val showDeleteDateConfirmationDialog: LiveData<Long?>
        get() = _showDeleteDateConfirmationDialog

    init {
        bindScope()
    }

    override fun bindScope() {
        val memorableDatesScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.MEMORABLE_DATES_SCOPE)
        Toothpick.inject(this, memorableDatesScope)
    }

    fun onAddDateClick() {
        router.navigateTo(Screens.getAddEditMemorableDateScreen())
    }

    fun onClearDatesClick() {
        if(dates.value.isNullOrEmpty()) return
        _showClearDateListConfirmationDialog.value = true
    }

    fun onClearDateListConfirmed() {
        _showClearDateListConfirmationDialog.value = false
        clearDateList()
    }

    private fun clearDateList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                clearMemorableDateListUseCase()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_clear_list))
            }
        }
    }

    fun onClearDateListCancelled() {
        _showClearDateListConfirmationDialog.value = false
    }

    fun onEditDateClick(date: MemorableDate) {
        val dateId = date.id ?: return
        router.navigateTo(Screens.getAddEditMemorableDateScreen(dateId))
    }

    fun onDeleteDateClick(date: MemorableDate) {
        val dateId = date.id ?: return
        _showDeleteDateConfirmationDialog.value = dateId
    }

    fun onDeleteDateConfirmed(dateId: Long) {
        _showDeleteDateConfirmationDialog.value = null
        deleteDate(dateId)
    }

    private fun deleteDate(dateId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                deleteMemorableDateFromIdUseCase(dateId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.deleting_item_error))
            }
        }
    }

    fun onDeleteDateCancelled() {
        _showDeleteDateConfirmationDialog.value = null
    }

    fun onDateLongClick(date: MemorableDate) {
        copyToClipboard(date.name)
        showMessage(Text.TextResource(R.string.text_copied))
    }

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.MEMORABLE_DATES_SCOPE)
        super.onCleared()
    }
}