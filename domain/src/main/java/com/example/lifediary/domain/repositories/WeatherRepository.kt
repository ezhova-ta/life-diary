package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.Location
import com.example.lifediary.domain.models.Weather
import com.example.lifediary.domain.models.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
	suspend fun findLocations(name: String): List<Location>
	fun getLocationFlow(): Flow<Location?>
	suspend fun getLocation(): Location?
	suspend fun saveLocation(location: Location)
	fun getCurrentWeather(): Flow<Weather?>
	suspend fun updateCurrentWeather(locationId: Long)
	suspend fun getForecast(locationId: Long): WeatherForecast
}