package com.example.lifediary.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.Location

@Entity(tableName = "location", indices = [Index(value = ["name"], unique = true)])
data class LocationEntity(
    @PrimaryKey
    val id: Long?,
    val name: String,
    val lat: Double,
    val lon: Double
) : DbEntity<Location>() {
    companion object {
        fun fromDomain(location: Location): LocationEntity {
            return LocationEntity(
                id = location.id,
                name = location.name,
                lat = location.lat,
                lon = location.lon
            )
        }
    }

    override fun toDomain(): Location {
        return Location(
            id = id,
            name = name,
            lat = lat,
            lon = lon
        )
    }
}
