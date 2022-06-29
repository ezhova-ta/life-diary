package com.example.lifediary.data.datasources

import com.example.lifediary.data.api.weather.CurrentWeatherResponse
import com.example.lifediary.data.api.weather.WeatherService
import com.example.lifediary.data.api.weather.forecast.WeatherForecastResponse
import com.example.lifediary.data.api.weather.location.LocationDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSource @Inject constructor(
    private val service: WeatherService
) {
    suspend fun findLocations(name: String): List<LocationDto> {
        return service.findLocations(name).locations
    }

    suspend fun getCurrentWeather(locationId: Long): CurrentWeatherResponse {
        return service.getCurrentWeather(locationId)
    }

    suspend fun getForecast(locationId: Long): WeatherForecastResponse {
        return service.getForecast(locationId)
    }
}