package com.example.lifediary.presentation.ui.woman_section

import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes.APP_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_ACTIVITY_VIEW_MODEL_SCOPE
import com.example.lifediary.di.DiScopes.MENSTRUATION_PERIOD_LIST_VIEW_MODEL_SCOPE
import com.example.lifediary.domain.models.MenstruationPeriod
import com.example.lifediary.domain.usecases.woman_section.AddMenstruationPeriodUseCase
import com.example.lifediary.domain.usecases.woman_section.ClearMenstruationPeriodListUseCase
import com.example.lifediary.domain.usecases.woman_section.DeleteMenstruationPeriodByIdUseCase
import com.example.lifediary.domain.usecases.woman_section.GetAllMenstruationPeriodsUseCase
import com.example.lifediary.domain.utils.CalendarBuilder
import com.example.lifediary.domain.utils.isDayAfter
import com.example.lifediary.domain.utils.toCalendar
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.presentation.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MenstruationPeriodListViewModel : BaseViewModel() {
    @Inject lateinit var getAllMenstruationPeriodsUseCase: GetAllMenstruationPeriodsUseCase
    @Inject lateinit var deleteMenstruationPeriodByIdUseCase: DeleteMenstruationPeriodByIdUseCase
    @Inject lateinit var clearMenstruationPeriodListUseCase: ClearMenstruationPeriodListUseCase
    @Inject lateinit var addMenstruationPeriodUseCase: AddMenstruationPeriodUseCase

    val menstruationPeriodList by lazy { getAllMenstruationPeriodsUseCase().asLiveData() }
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
        val womanSectionScope = Toothpick.openScopes(
            APP_SCOPE,
            MAIN_ACTIVITY_VIEW_MODEL_SCOPE,
            MENSTRUATION_PERIOD_LIST_VIEW_MODEL_SCOPE
        )
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
                deleteMenstruationPeriodByIdUseCase(periodId)
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
                clearMenstruationPeriodListUseCase()
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
                addMenstruationPeriodUseCase(period)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    fun onMenstruationPeriodPickerCancelled() {
        _showMenstruationPeriodPicker.value = false
    }

    override fun onCleared() {
        Toothpick.closeScope(MENSTRUATION_PERIOD_LIST_VIEW_MODEL_SCOPE)
        super.onCleared()
    }
}