package com.example.lifediary.ui.memorable_dates

import com.example.lifediary.data.repositories.MemorableDatesRepository
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MemorableDatesViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var memorableDatesRepository: MemorableDatesRepository

    init {
        bindAppScope()
    }

    fun onAddDateClick() {

    }

    fun onClearDatesClick() {

    }
}