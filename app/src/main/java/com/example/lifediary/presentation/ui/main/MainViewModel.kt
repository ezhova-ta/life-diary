package com.example.lifediary.presentation.ui.main

import androidx.lifecycle.*
import com.example.lifediary.R
import com.example.lifediary.di.DiScopes.APP_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_ACTIVITY_VIEW_MODEL_SCOPE
import com.example.lifediary.di.DiScopes.MAIN_SCREEN_VIEW_MODEL_SCOPE
import com.example.lifediary.domain.usecases.location.GetLocationLiveDataUseCase
import com.example.lifediary.domain.usecases.settings.GetMemorableDatesSectionEnabledUseCase
import com.example.lifediary.domain.usecases.settings.GetPostAddressesSectionEnabledUseCase
import com.example.lifediary.domain.usecases.settings.GetShoppingListSectionEnabledUseCase
import com.example.lifediary.domain.usecases.settings.GetWomanSectionEnabledUseCase
import com.example.lifediary.domain.usecases.weather.GetCurrentWeatherUseCase
import com.example.lifediary.domain.usecases.weather.UpdateCurrentWeatherUseCase
import com.example.lifediary.presentation.models.Text
import com.example.lifediary.presentation.navigation.Screens
import com.example.lifediary.presentation.ui.BaseViewModel
import com.example.lifediary.presentation.utils.formattedIconUrl
import com.example.lifediary.presentation.utils.temperatureFeelsLikeString
import com.example.lifediary.presentation.utils.temperatureString
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import toothpick.Toothpick
import javax.inject.Inject

class MainViewModel : BaseViewModel() {
    @Inject lateinit var router: Router
    @Inject lateinit var getLocationLiveDataUseCase: GetLocationLiveDataUseCase
    @Inject lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    @Inject lateinit var updateCurrentWeatherUseCase: UpdateCurrentWeatherUseCase
    @Inject lateinit var getShoppingListSectionEnabledUseCase: GetShoppingListSectionEnabledUseCase
    @Inject lateinit var getPostAddressesSectionEnabledUseCase: GetPostAddressesSectionEnabledUseCase
    @Inject lateinit var getMemorableDatesSectionEnabledUseCase: GetMemorableDatesSectionEnabledUseCase
    @Inject lateinit var getWomanSectionEnabledUseCase: GetWomanSectionEnabledUseCase

    val currentWeather by lazy { getCurrentWeatherUseCase().asLiveData() }
    val currentTemperature by lazy { currentWeather.map { it?.temperatureString } }
    val currentTemperatureFeelsLike by lazy { currentWeather.map { it?.temperatureFeelsLikeString} }
    val currentWeatherIconUrl by lazy { currentWeather.map { it?.formattedIconUrl } }
    private val locationFlow by lazy { getLocationLiveDataUseCase() }
    private val locationLiveData by lazy { locationFlow.asLiveData() }
    val locationName by lazy { locationLiveData.map { it?.name } }
    val isCurrentWeatherViewVisible by lazy { locationLiveData.map { it != null } }
    val isShoppingListSectionVisible by lazy { getShoppingListSectionEnabledUseCase().asLiveData() }
    val isPostAddressesSectionVisible by lazy { getPostAddressesSectionEnabledUseCase().asLiveData() }
    val isMemorableDatesSectionVisible by lazy { getMemorableDatesSectionEnabledUseCase().asLiveData() }
    val isWomanSectionVisible by lazy { getWomanSectionEnabledUseCase().asLiveData() }

    private val _isCurrentWeatherProgressVisible = MutableLiveData(false)
    val isCurrentWeatherProgressVisible: LiveData<Boolean>
        get() = _isCurrentWeatherProgressVisible

    init {
        bindScope()
        startListeningForLocation()
    }

    private fun startListeningForLocation() {
        viewModelScope.launch {
            locationFlow.collect { location ->
                location?.name?.let { updateCurrentWeather(it) }
            }
        }
    }

    override fun bindScope() {
        val mainScreenScope = Toothpick.openScopes(
            APP_SCOPE,
            MAIN_ACTIVITY_VIEW_MODEL_SCOPE,
            MAIN_SCREEN_VIEW_MODEL_SCOPE
        )
        Toothpick.inject(this, mainScreenScope)
    }

    fun onAvailabilityOfNetworkConnectivityChanged(isAvailable: Boolean) {
        if(isAvailable) updateCurrentWeather()
    }

    private fun updateCurrentWeather() {
        locationLiveData.value?.name?.let { updateCurrentWeather(it) }
    }

    private fun updateCurrentWeather(locationName: String) {
        val mutex = Mutex()
        _isCurrentWeatherProgressVisible.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                mutex.withLock { updateCurrentWeatherUseCase(locationName) }
            } catch(e: Exception) {
                showMessage(Text.TextResource(R.string.failed_to_update_weather_data))
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
        Toothpick.closeScope(MAIN_SCREEN_VIEW_MODEL_SCOPE)
        super.onCleared()
    }
}