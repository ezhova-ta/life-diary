package com.example.lifediary.data.datasources

import com.example.lifediary.data.api.WeatherService
import com.example.lifediary.data.domain.Location
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val service: WeatherService
) {
    suspend fun findLocation(name: String): List<Location> {
        return service.findLocation(name).locations.map { it.toDomain() }
    }
}