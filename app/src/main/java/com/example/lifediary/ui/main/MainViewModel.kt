package com.example.lifediary.ui.main

import androidx.lifecycle.*
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.domain.Weather
import com.example.lifediary.data.repositories.SettingsRepository
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MainViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var weatherRepository: WeatherRepository
    @Inject lateinit var settingsRepository: SettingsRepository
    val currentWeather: LiveData<Weather?> by lazy { weatherRepository.getCurrentWeather() }
    private val location: LiveData<Location?> by lazy { weatherRepository.getLocationLiveData() }
    val locationName: LiveData<String?> by lazy { location.map { it?.name } }
    val isCurrentWeatherViewVisible: LiveData<Boolean> by lazy { location.map { it != null } }
    val isShoppingListSectionVisible: LiveData<Boolean> by lazy { settingsRepository.getShoppingListSectionEnabled() }
    val isPostAddressesSectionVisible: LiveData<Boolean> by lazy { settingsRepository.getPostAddressesSectionEnabled() }
    val isMemorableDatesSectionVisible: LiveData<Boolean> by lazy { settingsRepository.getMemorableDatesSectionEnabled() }
    val isWomanSectionVisible: LiveData<Boolean> by lazy { settingsRepository.getWomanSectionEnabled() }

    private val _isCurrentWeatherProgressVisible = MutableLiveData(false)
    val isCurrentWeatherProgressVisible: LiveData<Boolean>
        get() = _isCurrentWeatherProgressVisible

    private val locationObserver = Observer<Location?> {
        val locationId = it?.id ?: return@Observer
        updateCurrentWeather(locationId)
    }

    init {
        bindScope()
        location.observeForever(locationObserver)
    }

    override fun bindScope() {
        val mainScreenScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.MAIN_SCREEN_SCOPE)
        Toothpick.inject(this, mainScreenScope)
    }

    fun onScreenResumed() {
        location.value?.id?.let {
            updateCurrentWeather(it)
        }
    }

    private fun updateCurrentWeather(locationId: Long) {
        _isCurrentWeatherProgressVisible.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                weatherRepository.updateCurrentWeather(locationId)
            } catch(e: Exception) {
                // TODO Message display temporarily removed
//                showMessage(Text.TextResource(R.string.failed_to_update_weather_data))
            } finally {
                _isCurrentWeatherProgressVisible.postValue(false)
            }
        }
    }

    fun onSettingsClick() {
        router.navigateTo(Screens.getSettingsScreen())
    }

    fun onLocationClick() {
        router.navigateTo(Screens.getLocationSelectionScreen())
    }

    fun onDateClick() {
        router.navigateTo(Screens.getCalendarScreen())
    }

    fun onNotesClick() {
        router.navigateTo(Screens.getMainNotesScreen())
    }

    fun onShoppingListClick() {
        router.navigateTo(Screens.getShoppingListScreen())
    }

    fun onPostAddressesClick() {
        router.navigateTo(Screens.getPostAddressesScreen())
    }

    fun onMemorableDatesClick() {
        router.navigateTo(Screens.getMemorableDatesScreen())
    }

    fun onWomanSectionClick() {
        router.navigateTo(Screens.getWomanSectionScreen())
    }

    override fun onCleared() {
        location.removeObserver(locationObserver)
        super.onCleared()
    }
}