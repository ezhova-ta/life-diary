package com.example.lifediary.ui.memorable_dates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifediary.R
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.repositories.MemorableDatesRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.MonthDropDownItem
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditMemorableDateViewModel(private val dateId: Long? = null) : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var memorableDatesRepository: MemorableDatesRepository
    val isAddButtonVisible = dateId == null
    val dateName = MutableLiveData("")
    val year = MutableLiveData("")
    var existingDate: MemorableDate? = null

    init {
        bindAppScope()
        substituteDateNameTextInInput()
    }

    private fun substituteDateNameTextInInput() {
        if(dateId == null) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                existingDate = memorableDatesRepository.getDate(dateId)
                existingDate?.name?.let { dateName.postValue(it) }
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    fun onDayNumberSelected(dayNumber: Int) {
        // TODO
    }

    fun onMonthSelected(month: MonthDropDownItem) {
        // TODO
    }

    fun onSaveDateClick() {
        TODO()
    }

    class Factory(private val dateId: Long?) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddEditMemorableDateViewModel(dateId) as T
        }
    }
}