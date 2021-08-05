package com.example.lifediary.ui.location.selection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifediary.data.domain.City
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationSelectionViewModel : BaseViewModel() {
    @Inject
    lateinit var repository: WeatherRepository

    val cityName = MutableLiveData("")
    val cities = MutableLiveData<List<City>>()
    val searchCityInputNeedsFocus = MutableLiveData(true)

    init {
        bindAppScope()
    }

    fun onSearchCityClick() {
        searchCityInputNeedsFocus.value = false
        val enteredCityName = formatCityName(cityName.value) ?: return
        if(enteredCityName.isBlank()) return

        Log.d("LocationSelectionDebugging", "input: $enteredCityName")

        viewModelScope.launch(Dispatchers.IO) {
            val foundCities = repository.findCity(enteredCityName)
            cities.postValue(foundCities)
        }
    }

    private fun formatCityName(name: String?) =
            name?.trim()

    fun onCityListItemClick(city: City) {
        Log.d("LocationSelectionDebugging", "click: ${city.name}")
    }
}