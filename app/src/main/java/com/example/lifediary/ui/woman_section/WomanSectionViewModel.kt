package com.example.lifediary.ui.woman_section

import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import toothpick.Toothpick

class WomanSectionViewModel : BaseViewModel() {
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

    fun onAddMenstruationClick() {
        _showMenstruationDatesPicker.value = true
    }

    fun onCancelMenstruationDatesPicker() {
        _showMenstruationDatesPicker.value = false
    }

    fun onMenstruationDatesSelected(dates: Pair<Long, Long>) {
        // TODO
        _showMenstruationDatesPicker.value = false
    }
}