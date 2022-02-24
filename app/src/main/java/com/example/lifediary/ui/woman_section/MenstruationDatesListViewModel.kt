package com.example.lifediary.ui.woman_section

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.R
import com.example.lifediary.data.domain.MenstruationDates
import com.example.lifediary.data.repositories.MenstruationDatesRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MenstruationDatesListViewModel : BaseViewModel() {
    @Inject lateinit var menstruationDatesRepository: MenstruationDatesRepository
    val menstruationDatesList: LiveData<List<MenstruationDates>> by lazy {
        menstruationDatesRepository.getAllMenstruationDates()
    }

    private val _showClearMenstruationDatesListConfirmationDialog = MutableLiveData(false)
    val showClearMenstruationDatesListConfirmationDialog: LiveData<Boolean>
        get() = _showClearMenstruationDatesListConfirmationDialog

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

    fun onClearMenstruationDatesListClick() {
        _showClearMenstruationDatesListConfirmationDialog.value = true
    }

    fun onClearMenstruationDatesListConfirmed() {
        _showClearMenstruationDatesListConfirmationDialog.value = false
        clearMenstruationDatesList()
    }

    private fun clearMenstruationDatesList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                menstruationDatesRepository.clearMenstruationDatesList()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_clear_list))
            }
        }
    }

    fun onClearMenstruationDatesListCancelled() {
        _showClearMenstruationDatesListConfirmationDialog.value = false
    }
}