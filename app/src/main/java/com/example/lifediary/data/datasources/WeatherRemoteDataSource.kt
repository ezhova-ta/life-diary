package com.example.lifediary.data.datasources

import com.example.lifediary.data.api.weather.WeatherService
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.domain.Weather
import com.example.lifediary.data.domain.WeatherForecast
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val service: WeatherService
) {
    suspend fun findLocations(name: String): List<Location> {
        return service.findLocations(name).locations.map { it.toDomain() }
    }

    suspend fun getCurrentWeather(locationId: Long): Weather {
        return service.getCurrentWeather(locationId).toDomain()
    }

    suspend fun getForecast(locationId: Long): WeatherForecast {
        return service.getForecast(locationId).toDomain()
    }
}