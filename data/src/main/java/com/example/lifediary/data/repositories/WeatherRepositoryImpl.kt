package com.example.lifediary.data.repositories

import com.example.lifediary.data.datasources.WeatherLocalDataSource
import com.example.lifediary.data.datasources.WeatherRemoteDataSource
import com.example.lifediary.data.repositories.mappers.api.CurrentWeatherResponseMapper.toDomain
import com.example.lifediary.data.repositories.mappers.api.LocationDtoMapper.toDomain
import com.example.lifediary.data.repositories.mappers.api.WeatherForecastResponseMapper.toDomain
import com.example.lifediary.data.repositories.mappers.db.LocationEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.db.LocationEntityMapper.toEntity
import com.example.lifediary.data.repositories.mappers.db.WeatherEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.db.WeatherEntityMapper.toEntity
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
        return remoteDataSource.findLocations(name).map { it.toDomain() }
    }

    override fun getLocationFlow(): Flow<Location?> {
        return localDataSource.getLocationFlow().map { it?.toDomain() }
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

    override suspend fun updateCurrentWeather(locationName: String) {
        val currentWeather = remoteDataSource.getCurrentWeather(locationName).toDomain()
        localDataSource.saveCurrentWeather(currentWeather.toEntity())
    }

    override suspend fun getForecast(locationName: String): WeatherForecast {
        return remoteDataSource.getForecast(locationName).toDomain()
    }
}