package com.example.lifediary.ui.woman_section

import androidx.lifecycle.LiveData
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.data.repositories.MenstruationDatesRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import toothpick.Toothpick
import javax.inject.Inject

class MenstruationDatesListViewModel : BaseViewModel() {
    @Inject lateinit var menstruationDatesRepository: MenstruationDatesRepository
    val menstruationDatesList: LiveData<List<MenstruationDates>> by lazy {
        menstruationDatesRepository.getAllMenstruationDates()
    }

    init {
        bindScope()
    }

    override fun bindScope() {
        val womanSectionScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.WOMAN_SECTION_SCOPE)
        Toothpick.inject(this, womanSectionScope)
    }

    fun onDeleteMenstruationDatesClick(dates: MenstruationDates) {
        TODO()
    }
}