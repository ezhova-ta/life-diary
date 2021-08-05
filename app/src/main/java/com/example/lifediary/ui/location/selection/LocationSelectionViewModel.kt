package com.example.lifediary.ui.location.selection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationSelectionViewModel : BaseViewModel() {
    @Inject
    lateinit var repository: WeatherRepository
    @Inject
    lateinit var router: Router

    val locationName = MutableLiveData("")
    val locations = MutableLiveData<List<Location>>()
    val searchLocationInputNeedsFocus = MutableLiveData(true)

    init {
        bindAppScope()
    }

    fun onSearchLocationClick() {
        searchLocationInputNeedsFocus.value = false
        val enteredLocationName = formatLocationName(locationName.value) ?: return
        if(enteredLocationName.isBlank()) return

        Log.d("LocationSelectionDebugging", "input: $enteredLocationName")

        viewModelScope.launch(Dispatchers.IO) {
            val foundLocations = repository.findLocation(enteredLocationName)
            locations.postValue(foundLocations)
        }
    }

    private fun formatLocationName(name: String?) =
            name?.trim()

    fun onLocationListItemClick(location: Location) {
        Log.d("LocationSelectionDebugging", "click: ${location.name}")
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveLocation(location)
            withContext(Dispatchers.Main) {
                router.exit()
            }
        }
    }
}