package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.WeatherRemoteDataSource
import com.example.lifediary.data.domain.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) {
    suspend fun findCity(name: String): List<City> {
        return remoteDataSource.findCity(name)
    }
}