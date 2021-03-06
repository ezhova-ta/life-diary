package com.example.lifediary.presentation.ui.memorable_dates

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.usecases.memorable_dates.AddMemorableDateUseCase
import com.example.lifediary.domain.usecases.memorable_dates.GetMemorableDateByIdUseCase
import com.example.lifediary.domain.usecases.memorable_dates.UpdateMemorableDateUseCase
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.presentation.models.dropdowns.DayNumberDropDownItem
import com.example.lifediary.presentation.models.dropdowns.MonthDropDownItem
import com.example.lifediary.presentation.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class AddEditMemorableDateViewModel(private val dateId: Long? = null) : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var getMemorableDateByIdUseCase: GetMemorableDateByIdUseCase
    @Inject lateinit var addMemorableDateUseCase: AddMemorableDateUseCase
    @Inject lateinit var updateMemorableDateUseCase: UpdateMemorableDateUseCase

    val isAddButtonVisible = dateId == null
    val dateName = MutableLiveData("")
    private var dayNumber: Int = DayNumberDropDownItem.allElements.first()
    private var month: MonthDropDownItem = MonthDropDownItem.JANUARY
    val year = MutableLiveData("")
    private var existingDate: MemorableDate? = null

    private val _existingDateDayNumber = MutableLiveData<Int>()
    val existingDateDayNumber: LiveData<Int> get() = _existingDateDayNumber

    private val _existingDateMonthNumber = MutableLiveData<Int>()
    val existingDateMonthNumber: LiveData<Int> get() = _existingDateMonthNumber

    init {
        bindScope()
        substituteDateNameTextInInput()
    }

    override fun bindScope() {
        val memorableDatesScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.MEMORABLE_DATES_SCOPE)
        Toothpick.inject(this, memorableDatesScope)
    }

    private fun substituteDateNameTextInInput() {
        if(dateId == null) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                existingDate = getMemorableDateByIdUseCase(dateId)
                existingDate?.let {
                    dateName.postValue(it.name)
                    _existingDateDayNumber.postValue(it.dayNumber)
                    _existingDateMonthNumber.postValue(it.monthNumber)
                    year.postValue(it.year?.toString() ?: "")
                }
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.error))
            }
        }
    }

    fun onDayNumberSelected(dayNumber: Int) {
        this.dayNumber = dayNumber
    }

    fun onMonthSelected(month: MonthDropDownItem) {
        this.month = month
    }

    fun onSaveDateClick() {
        val name = dateName.value?.trim()
        if(name.isNullOrBlank()) return

        val year = try {
            this.year.value?.trim()?.toInt()
        } catch(e: NumberFormatException) {
            null
        }

        saveDate(name, dayNumber, month.number, year)
    }

    private fun saveDate(name: String, dayNumber: Int, monthNumber: Int, year: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val date = existingDate

                if(date == null) {
                    addMemorableDateUseCase(MemorableDate(null, name, dayNumber, monthNumber,year))
                } else {
                    date.edit()
                        .setName(name)
                        .setDayNumber(dayNumber)
                        .setMonthNumber(monthNumber)
                        .setYear(year)
                        .apply()
                    updateMemorableDateUseCase(date)
                }

                router.exit()
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_save))
            }
        }
    }

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.MEMORABLE_DATES_SCOPE)
        super.onCleared()
    }

    class Factory(private val dateId: Long?) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddEditMemorableDateViewModel(dateId) as T
        }
    }
}