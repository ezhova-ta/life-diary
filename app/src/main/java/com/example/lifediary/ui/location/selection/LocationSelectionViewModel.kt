package com.example.lifediary.ui.location.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifediary.R
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.data.domain.Text
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
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
        bindScope()
    }

    override fun bindScope() {
        val locationSelectionScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.LOCATION_SELECTION_SCOPE)
        Toothpick.inject(this, locationSelectionScope)
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

    private fun formatLocationName(name: String?): String? {
        return name?.trim()
    }

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