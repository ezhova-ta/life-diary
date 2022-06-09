package com.example.lifediary.ui.woman_section

import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MenstruationPeriod
import com.example.lifediary.data.repositories.WomanSectionRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.data.domain.Text
import com.example.lifediary.utils.CalendarBuilder
import com.example.lifediary.utils.isDayAfter
import com.example.lifediary.utils.toCalendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import java.util.*
import javax.inject.Inject

class MenstruationPeriodListViewModel : BaseViewModel() {
    @Inject lateinit var womanSectionRepository: WomanSectionRepository
    val menstruationPeriodList by lazy { womanSectionRepository.getAllMenstruationPeriods() }
    val isMenstruationPeriodListVisible by lazy { menstruationPeriodList.map { it.isNotEmpty() } }

    private val _showDeleteMenstruationPeriodConfirmationDialog = MutableLiveData<Long?>(null)
    val showDeleteMenstruationPeriodConfirmationDialog: LiveData<Long?>
        get() = _showDeleteMenstruationPeriodConfirmationDialog

    private val _showClearMenstruationPeriodListConfirmationDialog = MutableLiveData(false)
    val showClearMenstruationPeriodListConfirmationDialog: LiveData<Boolean>
        get() = _showClearMenstruationPeriodListConfirmationDialog

    private val _showMenstruationPeriodPicker = MutableLiveData(false)
    val showMenstruationPeriodPicker: LiveData<Boolean>
        get() = _showMenstruationPeriodPicker

    init {
        bindScope()
    }

    override fun bindScope() {
        val womanSectionScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.WOMAN_SECTION_SCOPE)
        Toothpick.inject(this, womanSectionScope)
    }

    fun onDeleteMenstruationPeriodClick(period: MenstruationPeriod) {
        _showDeleteMenstruationPeriodConfirmationDialog.value = period.id
    }

    fun onDeleteMenstruationPeriodConfirmed(periodId: Long) {
        _showDeleteMenstruationPeriodConfirmationDialog.value = null
        deleteMenstruationPeriod(periodId)
    }

    private fun deleteMenstruationPeriod(periodId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                womanSectionRepository.deleteMenstruationPeriod(periodId)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.deleting_item_error))
            }
        }
    }

    fun onDeleteMenstruationPeriodCancelled() {
        _showDeleteMenstruationPeriodConfirmationDialog.value = null
    }

    fun onClearMenstruationPeriodListClick() {
        _showClearMenstruationPeriodListConfirmationDialog.value = true
    }

    fun onClearMenstruationPeriodListConfirmed() {
        _showClearMenstruationPeriodListConfirmationDialog.value = false
        clearMenstruationPeriodList()
    }

    private fun clearMenstruationPeriodList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                womanSectionRepository.clearMenstruationPeriodList()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_clear_list))
            }
        }
    }

    fun onClearMenstruationPeriodListCancelled() {
        _showClearMenstruationPeriodListConfirmationDialog.value = false
    }

    fun onAddMenstruationClick() {
        _showMenstruationPeriodPicker.value = true
    }

    fun onMenstruationPeriodSelected(period: Pair<Long, Long>) {
        addMenstruationPeriod(period)
        _showMenstruationPeriodPicker.value = false
    }

    private fun addMenstruationPeriod(period: Pair<Long, Long>) {
        val menstruationPeriod = MenstruationPeriod(
            startDate = period.first.toCalendar(),
            endDate = period.second.toCalendar()
        )
        addMenstruationPeriod(menstruationPeriod)
    }

    private fun addMenstruationPeriod(period: MenstruationPeriod) {
        val today = CalendarBuilder().build()

        if(period.startDate.isDayAfter(today)) {
            showMessage(Text.TextResource(R.string.start_menstruation_must_be_no_later_than_today))
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                womanSectionRepository.addMenstruationPeriod(period)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    fun onMenstruationPeriodPickerCancelled() {
        _showMenstruationPeriodPicker.value = false
    }

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.WOMAN_SECTION_SCOPE)
        super.onCleared()
    }
}