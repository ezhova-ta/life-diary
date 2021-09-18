package com.example.lifediary.ui.memorable_dates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifediary.data.repositories.MemorableDatesRepository
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class AddEditMemorableDateViewModel(private val dateId: Long? = null) : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var memorableDatesRepository: MemorableDatesRepository
    val isAddButtonVisible = dateId == null
    val dateName = MutableLiveData("")

    init {
        bindAppScope()
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