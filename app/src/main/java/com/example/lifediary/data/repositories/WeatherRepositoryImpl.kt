package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.WeatherLocalDataSource
import com.example.lifediary.data.datasources.WeatherRemoteDataSource
import com.example.lifediary.domain.models.Location
import com.example.lifediary.domain.models.Weather
import com.example.lifediary.domain.models.WeatherForecast
import com.example.lifediary.domain.repositories.WeatherRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {
    override suspend fun findLocations(name: String): List<Location> {
        return remoteDataSource.findLocations(name)
    }

    override fun getLocationLiveData(): LiveData<Location?> {
        return localDataSource.getLocationLiveData()
    }

    override suspend fun getLocation(): Location? {
        return localDataSource.getLocation()
    }

    override suspend fun saveLocation(location: Location) {
        localDataSource.saveLocation(location)
    }

    override fun getCurrentWeather(): LiveData<Weather?> {
        return localDataSource.getCurrentWeather()
    }

    override suspend fun updateCurrentWeather(locationId: Long) {
        val currentWeather = remoteDataSource.getCurrentWeather(locationId)
        localDataSource.saveCurrentWeather(currentWeather)
    }

    override suspend fun getForecast(locationId: Long): WeatherForecast {
        return remoteDataSource.getForecast(locationId)
    }
}