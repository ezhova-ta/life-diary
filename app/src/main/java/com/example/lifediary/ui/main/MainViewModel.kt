package com.example.lifediary.ui.main

import androidx.lifecycle.*
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.domain.Weather
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : BaseViewModel() {
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var repository: WeatherRepository

    private var location: LiveData<Location?>
    var locationName: LiveData<String?>
    var currentWeather: LiveData<Weather?>
    var isCurrentWeatherViewVisible: LiveData<Boolean>

    private val _isCurrentWeatherProgressVisible = MutableLiveData(false)
    val isCurrentWeatherProgressVisible: LiveData<Boolean>
        get() = _isCurrentWeatherProgressVisible

    // TODO Temp solution!
    private val locationObserver = Observer<Location?> {
        val locationId = it?.id ?: return@Observer
        updateCurrentWeather(locationId)
    }

    init {
        bindAppScope()
        location = repository.getLocation()
        locationName = location.map { it?.name }
        currentWeather = repository.getCurrentWeather()
        isCurrentWeatherViewVisible = location.map { it != null }

        // TODO Temp solution!
        location.observeForever(locationObserver)
    }

    // TODO True solution?
    fun onScreenResumed() {
        location.value?.id?.let {
            updateCurrentWeather(it)
        }
    }

    private fun updateCurrentWeather(locationId: Long) {
        _isCurrentWeatherProgressVisible.value = true

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCurrentWeather(locationId)
            _isCurrentWeatherProgressVisible.postValue(false)
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

    fun onShoppingListClick() {
        router.navigateTo(Screens.getShoppingListScreen())
    }

    override fun onCleared() {
        location.removeObserver(locationObserver)
        super.onCleared()
    }
}