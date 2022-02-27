package com.example.lifediary.ui.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.data.repositories.MenstruationDatesRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class WomanSectionViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var menstruationDatesRepository: MenstruationDatesRepository
    val lastMenstruationDates: LiveData<MenstruationDates?> by lazy {
        menstruationDatesRepository.getAllMenstruationDates().map { it.maxByOrNull { it.startDate } }
    }
    val lengthOfMenstrualCycle = MutableLiveData<Int>()

    private val _showSetLengthOfMenstrualCycleDialog = MutableLiveData(false)
    val showSetLengthOfMenstrualCycleDialog: LiveData<Boolean>
        get() = _showSetLengthOfMenstrualCycleDialog

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

    fun onSetLengthOfMenstrualCycleClick() {
        _showSetLengthOfMenstrualCycleDialog.value = true
    }

    fun onLengthOfMenstrualCycleHasBeenSet(value: Int) {
        _showSetLengthOfMenstrualCycleDialog.value = false
        lengthOfMenstrualCycle.value = value
        saveLengthOfMenstrualCycle(value)
    }

    private fun saveLengthOfMenstrualCycle(value: Int) {
        // TODO
    }

    fun onSetLengthOfMenstrualCycleCancelled() {
        _showSetLengthOfMenstrualCycleDialog.value = false
    }
}