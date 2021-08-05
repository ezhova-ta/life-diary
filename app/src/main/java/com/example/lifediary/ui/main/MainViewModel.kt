package com.example.lifediary.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.repositories.WeatherRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel : BaseViewModel() {
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var repository: WeatherRepository

    private var location: LiveData<Location?>
    var locationName: LiveData<String?>

    init {
        bindAppScope()
        location = repository.getLocation()
        locationName = location.map { it?.name }
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
}