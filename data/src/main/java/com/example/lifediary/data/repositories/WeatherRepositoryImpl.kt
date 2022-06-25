package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.WeatherLocalDataSource
import com.example.lifediary.data.datasources.WeatherRemoteDataSource
import com.example.lifediary.data.repositories.mappers.LocationEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.LocationEntityMapper.toEntity
import com.example.lifediary.data.repositories.mappers.WeatherEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.WeatherEntityMapper.toEntity
import com.example.lifediary.domain.models.Location
import com.example.lifediary.domain.models.Weather
import com.example.lifediary.domain.models.WeatherForecast
import com.example.lifediary.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getLocationLiveData(): Flow<Location?> {
        return localDataSource.getLocationLiveData().map { it?.toDomain() }
    }

    override suspend fun getLocation(): Location? {
        return localDataSource.getLocation()?.toDomain()
    }

    override suspend fun saveLocation(location: Location) {
        localDataSource.saveLocation(location.toEntity())
    }

    override fun getCurrentWeather(): Flow<Weather?> {
        return localDataSource.getCurrentWeather().map { it?.toDomain() }
    }

    override suspend fun updateCurrentWeather(locationId: Long) {
        val currentWeather = remoteDataSource.getCurrentWeather(locationId)
        localDataSource.saveCurrentWeather(currentWeather.toEntity())
    }

    override suspend fun getForecast(locationId: Long): WeatherForecast {
        return remoteDataSource.getForecast(locationId)
    }
}