package com.example.lifediary.data.datasources

import com.example.lifediary.data.api.weather.WeatherService
import com.example.lifediary.domain.models.Location
import com.example.lifediary.domain.models.Weather
import com.example.lifediary.domain.models.WeatherForecast
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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