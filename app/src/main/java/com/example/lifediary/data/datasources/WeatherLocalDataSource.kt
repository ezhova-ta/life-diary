package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.dao.LocationDao
import com.example.lifediary.data.db.entities.LocationEntity
import com.example.lifediary.data.domain.Location
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val dao: LocationDao
) {
    fun getLocation(): LiveData<Location?> {
        return dao.getLocation().map { it?.toDomain() }
    }

    suspend fun saveLocation(location: Location) {
        dao.delete()
        dao.insert(LocationEntity.fromDomain(location))
    }

    suspend fun clearLocation() {
        dao.delete()
    }
}