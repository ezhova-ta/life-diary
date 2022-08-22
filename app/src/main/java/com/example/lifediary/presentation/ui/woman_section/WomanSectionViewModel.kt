package com.example.lifediary.presentation.ui.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes.APP_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_ACTIVITY_VIEW_MODEL_SCOPE
import com.example.lifediary.di.DiScopes.WOMAN_SECTION_VIEW_MODEL_SCOPE
import com.example.lifediary.domain.usecases.woman_section.*
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.presentation.navigation.Screens
import com.example.lifediary.presentation.ui.BaseViewModel
import com.example.lifediary.presentation.utils.toOutputFormattedString
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class WomanSectionViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var getLastMenstruationPeriodUseCase: GetLastMenstruationPeriodUseCase
    @Inject lateinit var getDurationOfMenstrualCycleUseCase: GetDurationOfMenstrualCycleUseCase
    @Inject lateinit var getDurationOfMenstruationPeriodUseCase: GetDurationOfMenstruationPeriodUseCase
    @Inject lateinit var getEstimatedNextMenstruationPeriodUseCase: GetEstimatedNextMenstruationPeriodUseCase
    @Inject lateinit var getDelayOfMenstruationUseCase: GetDelayOfMenstruationUseCase
    @Inject lateinit var saveDurationOfMenstrualCycleUseCase: SaveDurationOfMenstrualCycleUseCase
    @Inject lateinit var saveDurationOfMenstruationPeriodUseCase: SaveDurationOfMenstruationPeriodUseCase

    val lastMenstruationPeriodString by lazy {
        getLastMenstruationPeriodUseCase().map { it?.toOutputFormattedString() }.asLiveData()
    }
    val estimatedNextMenstruationPeriodString by lazy {
        getEstimatedNextMenstruationPeriodUseCase().map { it?.toOutputFormattedString() }.asLiveData()
    }
    val durationOfMenstrualCycle by lazy { getDurationOfMenstrualCycleUseCase().asLiveData() }
    val durationOfMenstruationPeriod by lazy { getDurationOfMenstruationPeriodUseCase().asLiveData() }
    val delayOfMenstruation by lazy { getDelayOfMenstruationUseCase().asLiveData() }
    val delayOfMenstruationVisibility by lazy { delayOfMenstruation.map { isDelayOfMenstruationValid(it) } }

    private val _showSetDurationOfMenstrualCycleDialog = MutableLiveData(false)
    val showSetDurationOfMenstrualCycleDialog: LiveData<Boolean>
        get() = _showSetDurationOfMenstrualCycleDialog

    private val _showSetDurationOfMenstruationPeriodDialog = MutableLiveData(false)
    val showSetDurationOfMenstruationPeriodDialog: LiveData<Boolean>
        get() = _showSetDurationOfMenstruationPeriodDialog

    init {
        bindScope()
    }

    private fun isDelayOfMenstruationValid(value: Long?): Boolean {
        return value != null && value != 0L
    }

    override fun bindScope() {
        val womanSectionScope = Toothpick.openScopes(
            APP_SCOPE,
            MAIN_ACTIVITY_VIEW_MODEL_SCOPE,
            WOMAN_SECTION_VIEW_MODEL_SCOPE
        )
        Toothpick.inject(this, womanSectionScope)
    }

    fun onShowMenstruationPeriodListClick() {
        router.navigateTo(Screens.getMenstruationPeriodListScreen())
    }

    fun onSetDurationOfMenstrualCycleClick() {
        _showSetDurationOfMenstrualCycleDialog.value = true
    }

    fun onDurationOfMenstrualCycleHasBeenSet(value: Int) {
        _showSetDurationOfMenstrualCycleDialog.value = false
        saveDurationOfMenstrualCycle(value)
    }

    private fun saveDurationOfMenstrualCycle(value: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveDurationOfMenstrualCycleUseCase(value)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    fun onSetDurationOfMenstrualCycleCancelled() {
        _showSetDurationOfMenstrualCycleDialog.value = false
    }

    fun onSetDurationOfMenstruationPeriodClick() {
        _showSetDurationOfMenstruationPeriodDialog.value = true
    }

    fun onDurationOfMenstruationPeriodHasBeenSet(value: Int) {
        _showSetDurationOfMenstruationPeriodDialog.value = false
        saveDurationOfMenstruationPeriod(value)
    }

    private fun saveDurationOfMenstruationPeriod(value: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveDurationOfMenstruationPeriodUseCase(value)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    fun onSetDurationOfMenstruationPeriodCancelled() {
        _showSetDurationOfMenstruationPeriodDialog.value = false
    }

    override fun onCleared() {
        Toothpick.closeScope(WOMAN_SECTION_VIEW_MODEL_SCOPE)
        super.onCleared()
    }
}