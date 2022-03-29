package com.example.lifediary.ui.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.repositories.WomanSectionRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.data.domain.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class WomanSectionViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var womanSectionRepository: WomanSectionRepository
    val lastMenstruationPeriod by lazy { womanSectionRepository.getLastMenstruationPeriod() }
    val durationOfMenstrualCycle by lazy { womanSectionRepository.getDurationOfMenstrualCycle() }
    val durationOfMenstruationPeriod by lazy { womanSectionRepository.getDurationOfMenstruationPeriod() }
    val estimatedNextMenstruationPeriod by lazy { womanSectionRepository.getEstimatedNextMenstruationPeriod() }
    val delayOfMenstruation by lazy { womanSectionRepository.getDelayOfMenstruation() }
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
                womanSectionRepository.setDurationOfMenstrualCycle(value)
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
                womanSectionRepository.setDurationOfMenstruationPeriod(value)
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