package com.example.lifediary.ui.memorable_dates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.repositories.MemorableDatesRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.sortBasedToday
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MemorableDatesViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var memorableDatesRepository: MemorableDatesRepository
    val dates: LiveData<List<MemorableDate>> by lazy {
        memorableDatesRepository.getDates().map { it.sortBasedToday() }
    }
    val isDatesVisible: LiveData<Boolean> by lazy { dates.map { it.isNotEmpty() } }

    private val _showClearDateListConfirmationDialog = MutableLiveData(false)
    val showClearDateListConfirmationDialog: LiveData<Boolean>
        get() = _showClearDateListConfirmationDialog

    private val _showDeleteDateConfirmationDialog = MutableLiveData<Long?>(null)
    val showDeleteDateConfirmationDialog: LiveData<Long?>
        get() = _showDeleteDateConfirmationDialog

    init {
        bindAppScope()
    }

    fun onAddDateClick() {
        router.navigateTo(Screens.getAddEditMemorableDateFragment())
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
                memorableDatesRepository.clearDates()
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
        router.navigateTo(Screens.getAddEditMemorableDateFragment(dateId))
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
                memorableDatesRepository.deleteDate(dateId)
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
}