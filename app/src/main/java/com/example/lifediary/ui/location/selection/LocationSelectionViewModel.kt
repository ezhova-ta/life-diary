package com.example.lifediary.ui.location.selection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lifediary.ui.BaseViewModel

class LocationSelectionViewModel : BaseViewModel() {
    val cityName = MutableLiveData("")
    val searchCityInputNeedsFocus = MutableLiveData(true)

    init {
        bindAppScope()
    }

    fun onSearchCityClick() {
        searchCityInputNeedsFocus.value = false
        val enteredCityName = cityName.value ?: return
        Log.d("LocationSelectionDebugging", enteredCityName)
        // TODO Search cities by entered name
    }
}