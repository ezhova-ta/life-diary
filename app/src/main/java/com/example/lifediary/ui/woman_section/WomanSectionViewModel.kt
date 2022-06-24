package com.example.lifediary.ui.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.Text
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.usecases.woman_section.*
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    val lastMenstruationPeriod by lazy { getLastMenstruationPeriodUseCase() }
    val durationOfMenstrualCycle by lazy { getDurationOfMenstrualCycleUseCase() }
    val durationOfMenstruationPeriod by lazy { getDurationOfMenstruationPeriodUseCase() }
    val estimatedNextMenstruationPeriod by lazy { getEstimatedNextMenstruationPeriodUseCase() }
    val delayOfMenstruation by lazy { getDelayOfMenstruationUseCase() }
    val delayOfMenstruationVisibility by lazy { delayOfMenstruation.map { it != null && it != 0L } }

    private val _showSetDurationOfMenstrualCycleDialog = MutableLiveData(false)
    val showSetDurationOfMenstrualCycleDialog: LiveData<Boolean>
        get() = _showSetDurationOfMenstrualCycleDialog

    private val _showSetDurationOfMenstruationPeriodDialog = MutableLiveData(false)
    val showSetDurationOfMenstruationPeriodDialog: LiveData<Boolean>
        get() = _showSetDurationOfMenstruationPeriodDialog

    init {
        bindScope()
    }

    override fun bindScope() {
        val womanSectionScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.WOMAN_SECTION_SCOPE)
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
        Toothpick.closeScope(DiScopes.WOMAN_SECTION_SCOPE)
        super.onCleared()
    }
}