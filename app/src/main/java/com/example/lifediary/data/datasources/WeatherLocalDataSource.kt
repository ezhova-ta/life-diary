package com.example.lifediary.data.datasources

import com.example.lifediary.data.db.dao.LocationDao
import com.example.lifediary.data.db.entities.LocationEntity
import com.example.lifediary.data.domain.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val dao: LocationDao
) {
    fun getLocationFlow(): Flow<Location?> {
        return dao.getLocationFlow().map { it?.toDomain() }
    }

    suspend fun saveLocation(location: Location) {
        dao.delete()
        dao.insert(LocationEntity.fromDomain(location))
    }

    suspend fun clearLocation() {
        dao.delete()
    }
}