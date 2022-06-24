package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.Location
import com.example.lifediary.domain.models.Weather
import com.example.lifediary.domain.models.WeatherForecast

interface WeatherRepository {
	suspend fun findLocations(name: String): List<Location>
	fun getLocationLiveData(): LiveData<Location?>
	suspend fun getLocation(): Location?
	suspend fun saveLocation(location: Location)
	fun getCurrentWeather(): LiveData<Weather?>
	suspend fun updateCurrentWeather(locationId: Long)
	suspend fun getForecast(locationId: Long): WeatherForecast
}