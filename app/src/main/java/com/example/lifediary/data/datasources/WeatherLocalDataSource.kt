package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.CurrentWeatherDao
import com.example.lifediary.data.db.dao.LocationDao
import com.example.lifediary.data.db.entities.LocationEntity
import com.example.lifediary.data.db.entities.WeatherEntity
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val locationDao: LocationDao,
    private val currentWeatherDao: CurrentWeatherDao
) {
    fun getLocationLiveData(): LiveData<LocationEntity?> {
        return locationDao.getLiveData()
    }

    suspend fun getLocation(): LocationEntity? {
        return locationDao.get()
    }

    suspend fun saveLocation(location: LocationEntity) {
        locationDao.delete()
        locationDao.insert(location)
    }

    fun getCurrentWeather(): LiveData<WeatherEntity?> {
        return currentWeatherDao.get()
    }

    suspend fun saveCurrentWeather(weather: WeatherEntity) {
        currentWeatherDao.delete()
        currentWeatherDao.insert(weather)
    }
}