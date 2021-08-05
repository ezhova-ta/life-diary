package com.example.lifediary.data.datasources

import com.example.lifediary.data.api.WeatherService
import com.example.lifediary.data.domain.City
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val service: WeatherService
) {
    suspend fun findCity(name: String): List<City> {
        return service.findCity(name).cities.map { it.toDomain() }
    }
}