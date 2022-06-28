package com.example.lifediary.presentation.ui.location.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lifediary.R
import com.example.lifediary.domain.models.Location
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.usecases.location.FindLocationsUseCase
import com.example.lifediary.domain.usecases.location.SaveLocationUseCase
import com.example.lifediary.presentation.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class LocationSelectionViewModel : BaseViewModel() {
    @Inject lateinit var saveLocationUseCase: SaveLocationUseCase
    @Inject lateinit var findLocationsUseCase: FindLocationsUseCase
    @Inject lateinit var router: Router
    val locationName = MutableLiveData("")

    private val _isProgressVisible = MutableLiveData(false)
    val isProgressVisible get() = _isProgressVisible

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> get() = _locations

    private val _inputNeedsFocus = MutableLiveData(true)
    val inputNeedsFocus: LiveData<Boolean> get() = _inputNeedsFocus

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
        _isProgressVisible.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val foundLocations = findLocationsUseCase(enteredLocationName)
                _locations.postValue(foundLocations)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.search_location_error))
            } finally {
                _isProgressVisible.postValue(false)
            }
        }
    }

    private fun formatLocationName(name: String?): String? {
        return name?.trim()
    }

    fun onLocationListItemClick(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                saveLocationUseCase(location)
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.saving_location_error))
            }
        }
        router.exit()
    }

    override fun onCleared() {
        Toothpick.closeScope(DiScopes.LOCATION_SELECTION_SCOPE)
        super.onCleared()
    }
}