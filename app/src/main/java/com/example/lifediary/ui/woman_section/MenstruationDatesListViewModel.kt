package com.example.lifediary.ui.woman_section

import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.R
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.data.repositories.WomanSectionRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.toCalendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MenstruationDatesListViewModel : BaseViewModel() {
    @Inject lateinit var womanSectionRepository: WomanSectionRepository
    val menstruationDatesList: LiveData<List<MenstruationDates>> by lazy {
        womanSectionRepository.getAllMenstruationDates()
    }

    private val _showDeleteMenstruationDatesConfirmationDialog = MutableLiveData<Long?>(null)
    val showDeleteMenstruationDatesConfirmationDialog: LiveData<Long?>
        get() = _showDeleteMenstruationDatesConfirmationDialog

    private val _showClearMenstruationDatesListConfirmationDialog = MutableLiveData(false)
    val showClearMenstruationDatesListConfirmationDialog: LiveData<Boolean>
        get() = _showClearMenstruationDatesListConfirmationDialog

    private val _showMenstruationDatesPicker = MutableLiveData(false)
    val showMenstruationDatesPicker: LiveData<Boolean>
        get() = _showMenstruationDatesPicker

    init {
        bindScope()
    }

    override fun bindScope() {
        val womanSectionScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.WOMAN_SECTION_SCOPE)
        Toothpick.inject(this, womanSectionScope)
    }

    fun onDeleteMenstruationDatesClick(dates: MenstruationDates) {
        _showDeleteMenstruationDatesConfirmationDialog.value = dates.id
    }

    fun onDeleteMenstruationDatesConfirmed(datesId: Long) {
        _showDeleteMenstruationDatesConfirmationDialog.value = null
        deleteMenstruationDates(datesId)
    }

    private fun deleteMenstruationDates(datesId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                womanSectionRepository.deleteMenstruationDates(datesId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.deleting_item_error))
            }
        }
    }

    fun onDeleteMenstruationDatesCancelled() {
        _showDeleteMenstruationDatesConfirmationDialog.value = null
    }

    fun onClearMenstruationDatesListClick() {
        _showClearMenstruationDatesListConfirmationDialog.value = true
    }

    fun onClearMenstruationDatesListConfirmed() {
        _showClearMenstruationDatesListConfirmationDialog.value = false
        clearMenstruationDatesList()
    }

    private fun clearMenstruationDatesList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                womanSectionRepository.clearMenstruationDatesList()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_clear_list))
            }
        }
    }

    fun onClearMenstruationDatesListCancelled() {
        _showClearMenstruationDatesListConfirmationDialog.value = false
    }

    fun onAddMenstruationClick() {
        _showMenstruationDatesPicker.value = true
    }

    fun onMenstruationDatesSelected(dates: Pair<Long, Long>) {
        addMenstruationDates(dates)
        _showMenstruationDatesPicker.value = false
    }

    private fun addMenstruationDates(dates: Pair<Long, Long>) {
        val menstruationDates = MenstruationDates(
            startDate = dates.first.toCalendar(),
            endDate = dates.second.toCalendar()
        )
        addMenstruationDates(menstruationDates)
    }

    private fun addMenstruationDates(dates: MenstruationDates) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                womanSectionRepository.addMenstruationDates(dates)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    fun onMenstruationDatesPickerCancelled() {
        _showMenstruationDatesPicker.value = false
    }
}