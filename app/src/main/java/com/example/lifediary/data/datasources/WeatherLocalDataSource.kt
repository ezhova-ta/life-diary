package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.CurrentWeatherDao
import com.example.lifediary.data.db.dao.LocationDao
import com.example.lifediary.data.db.entities.LocationEntity
import com.example.lifediary.data.db.entities.WeatherEntity
import com.example.lifediary.data.domain.Location
import com.example.lifediary.data.domain.Weather
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val locationDao: LocationDao,
    private val currentWeatherDao: CurrentWeatherDao
) {
    fun getLocation(): LiveData<Location?> {
        return locationDao.getLocation().map { it?.toDomain() }
    }

    suspend fun saveLocation(location: Location) {
        locationDao.delete()
        locationDao.insert(LocationEntity.fromDomain(location))
    }

    fun getCurrentWeather(): LiveData<Weather?> {
        return currentWeatherDao.get().map { it?.toDomain() }
    }

    suspend fun saveCurrentWeather(weather: Weather) {
        currentWeatherDao.delete()
        currentWeatherDao.insert(WeatherEntity.fromDomain(weather))
    }
}