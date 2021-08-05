package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.WeatherLocalDataSource
import com.example.lifediary.data.datasources.WeatherRemoteDataSource
import com.example.lifediary.data.domain.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) {
    suspend fun findLocation(name: String): List<Location> {
        return remoteDataSource.findLocation(name)
    }

    fun getLocationFlow(): Flow<Location?> {
        return localDataSource.getLocationFlow()
    }

    suspend fun saveLocation(location: Location) {
        localDataSource.saveLocation(location)
    }

    suspend fun clearLocation() {
        localDataSource.clearLocation()
    }
}