package com.example.lifediary.data.api.weather.location

import com.example.lifediary.data.domain.Location
import com.google.gson.annotations.SerializedName

data class LocationDto(
    val id: Long?,
    val name: String,
    @SerializedName("coord")
    val coordinates: CoordinatesDto
) {
    companion object {
        fun fromDomain(location: Location): LocationDto {
            return LocationDto(
                id = location.id,
                name = location.name,
                coordinates = CoordinatesDto(location.lat, location.lon)
            )
        }
    }

    fun toDomain(): Location {
        return Location(
            id = id,
            name = name,
            lat = coordinates.lat,
            lon = coordinates.lon
        )
    }
}