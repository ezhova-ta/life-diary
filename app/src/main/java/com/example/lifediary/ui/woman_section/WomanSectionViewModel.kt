package com.example.lifediary.ui.woman_section

import android.util.Log
import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.data.repositories.MenstruationDatesRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.example.lifediary.utils.toCalendar
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class WomanSectionViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var menstruationDatesRepository: MenstruationDatesRepository
    val menstruationDatesList: LiveData<List<MenstruationDates>> by lazy {
        menstruationDatesRepository.getAllMenstruationDates()
    }
    val lastMenstruationDates: LiveData<MenstruationDates?> by lazy {
        menstruationDatesList.map { it.maxByOrNull { it.startDate } }
    }

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

    fun onShowMenstruationDatesListClick() {
        router.navigateTo(Screens.getMenstruationDatesListScreen())
    }

    fun onAddMenstruationClick() {
        _showMenstruationDatesPicker.value = true
    }

    fun onCancelMenstruationDatesPicker() {
        _showMenstruationDatesPicker.value = false
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
                menstruationDatesRepository.addMenstruationDates(dates)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }
}