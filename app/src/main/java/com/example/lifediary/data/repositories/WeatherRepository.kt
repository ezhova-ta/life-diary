package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.WeatherLocalDataSource
import com.example.lifediary.data.datasources.WeatherRemoteDataSource
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.domain.Weather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) {
    suspend fun findLocations(name: String): List<Location> {
        return remoteDataSource.findLocations(name)
    }

    fun getLocation(): LiveData<Location?> {
        return localDataSource.getLocation()
    }

    suspend fun saveLocation(location: Location) {
        localDataSource.saveLocation(location)
    }

    suspend fun getCurrentWeather(locationId: Long): Weather {
        return remoteDataSource.getCurrentWeather(locationId)
    }
}