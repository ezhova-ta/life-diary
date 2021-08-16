package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.WeatherLocalDataSource
import com.example.lifediary.data.datasources.WeatherRemoteDataSource
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.domain.Weather
import com.example.lifediary.data.domain.WeatherForecast
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

    fun getLocationLiveData(): LiveData<Location?> {
        return localDataSource.getLocationLiveData()
    }

    fun getLocation(): Location? {
        return localDataSource.getLocation()
    }

    suspend fun saveLocation(location: Location) {
        localDataSource.saveLocation(location)
    }

    fun getCurrentWeather(): LiveData<Weather?> {
        return localDataSource.getCurrentWeather()
    }

    suspend fun updateCurrentWeather(locationId: Long) {
        val currentWeather = remoteDataSource.getCurrentWeather(locationId)
        localDataSource.saveCurrentWeather(currentWeather)
    }

    suspend fun getForecast(locationId: Long): WeatherForecast {
        return remoteDataSource.getForecast(locationId)
    }
}