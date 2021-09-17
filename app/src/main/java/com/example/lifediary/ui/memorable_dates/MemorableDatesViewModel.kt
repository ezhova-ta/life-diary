package com.example.lifediary.ui.memorable_dates

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.R
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.repositories.MemorableDatesRepository
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

    init {
        bindAppScope()
    }

    fun onAddDateClick() {
        TODO()
    }

    fun onClearDatesClick() {
        TODO()
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