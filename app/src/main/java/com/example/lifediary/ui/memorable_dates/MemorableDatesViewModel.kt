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
        TODO()
    }

    fun onDeleteDateClick(date: MemorableDate) {
        TODO()
    }

    fun onDateLongClick(date: MemorableDate) {
        TODO()
    }
}