package com.example.lifediary.ui.location.selection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationSelectionViewModel : BaseViewModel() {
    @Inject
    lateinit var repository: WeatherRepository
    @Inject
    lateinit var router: Router

    val locationName = MutableLiveData("")
    val locations = MutableLiveData<List<Location>>()
    val searchLocationInputNeedsFocus = MutableLiveData(true)
    val isProgressVisible = MutableLiveData(false)

    init {
        bindAppScope()
    }

    fun onSearchLocationClick() {
        searchLocationInputNeedsFocus.value = false
        val enteredLocationName = formatLocationName(locationName.value) ?: return
        if(enteredLocationName.isBlank()) return
        isProgressVisible.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val foundLocations = repository.findLocations(enteredLocationName)
            isProgressVisible.postValue(false)
            locations.postValue(foundLocations)
        }
    }

    private fun formatLocationName(name: String?) =
            name?.trim()

    fun onLocationListItemClick(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveLocation(location)
        }
        router.exit()
    }
}