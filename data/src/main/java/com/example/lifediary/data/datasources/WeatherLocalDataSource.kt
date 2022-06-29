package com.example.lifediary.data.datasources

import com.example.lifediary.data.db.dao.CurrentWeatherDao
import com.example.lifediary.data.db.dao.LocationDao
import com.example.lifediary.data.db.models.LocationEntity
import com.example.lifediary.data.db.models.WeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherLocalDataSource @Inject constructor(
    private val locationDao: LocationDao,
    private val currentWeatherDao: CurrentWeatherDao
) {
    fun getLocationFlow(): Flow<LocationEntity?> {
        return locationDao.getLiveData()
    }

    suspend fun getLocation(): LocationEntity? {
        return locationDao.get()
    }

    suspend fun saveLocation(location: LocationEntity) {
        locationDao.delete()
        locationDao.insert(location)
    }

    fun getCurrentWeather(): Flow<WeatherEntity?> {
        return currentWeatherDao.get()
    }

    suspend fun saveCurrentWeather(weather: WeatherEntity) {
        currentWeatherDao.delete()
        currentWeatherDao.insert(weather)
    }
}