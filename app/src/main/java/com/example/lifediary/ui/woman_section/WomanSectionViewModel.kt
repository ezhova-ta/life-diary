package com.example.lifediary.ui.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.data.repositories.WomanSectionRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class WomanSectionViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var womanSectionRepository: WomanSectionRepository
    val lastMenstruationDates: LiveData<MenstruationDates?> by lazy {
        womanSectionRepository.getAllMenstruationDates().map { it.maxByOrNull { it.startDate } }
    }
    val durationOfMenstrualCycle: LiveData<Int> by lazy {
        womanSectionRepository.getDurationOfMenstrualCycle()
    }

    private val _showSetDurationOfMenstrualCycleDialog = MutableLiveData(false)
    val showSetDurationOfMenstrualCycleDialog: LiveData<Boolean>
        get() = _showSetDurationOfMenstrualCycleDialog

    init {
        bindScope()
    }

    override fun bindScope() {
        val womanSectionScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.WOMAN_SECTION_SCOPE)
        Toothpick.inject(this, womanSectionScope)
    }

    fun onShowMenstruationDatesListClick() {
        router.navigateTo(Screens.getMenstruationDatesListScreen())
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
}