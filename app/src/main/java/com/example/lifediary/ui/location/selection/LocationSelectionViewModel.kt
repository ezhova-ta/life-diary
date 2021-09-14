package com.example.lifediary.ui.location.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifediary.R
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationSelectionViewModel : BaseViewModel() {
    @Inject lateinit var weatherRepository: WeatherRepository
    @Inject lateinit var router: Router
    val locationName = MutableLiveData("")
    val isProgressVisible = MutableLiveData(false)

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>>
        get() = _locations

    private val _inputNeedsFocus = MutableLiveData(true)
    val inputNeedsFocus: LiveData<Boolean>
        get() = _inputNeedsFocus

    init {
        bindAppScope()
    }

    fun onSearchLocationClick() {
        onSearchLocationInputDone()
    }

    fun onSearchLocationInputDone() {
        _inputNeedsFocus.value = false
        val enteredLocationName = formatLocationName(locationName.value) ?: return
        if(enteredLocationName.isBlank()) return
        isProgressVisible.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foundLocations = weatherRepository.findLocations(enteredLocationName)
                _locations.postValue(foundLocations)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.search_location_error))
            } finally {
                isProgressVisible.postValue(false)
            }
        }
    }

    private fun formatLocationName(name: String?) =
            name?.trim()

    fun onLocationListItemClick(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                weatherRepository.saveLocation(location)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.saving_location_error))
            }
        }
        router.exit()
    }
}