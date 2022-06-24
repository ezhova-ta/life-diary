package com.example.lifediary.data.api.weather.location

import com.example.lifediary.domain.models.Location
import com.google.gson.annotations.SerializedName

data class LocationDto(
    val id: Long?,
    val name: String,
    @SerializedName("coord")
    val coordinates: CoordinatesDto
) {
    fun toDomain(): Location {
        return Location(
            id = id,
            name = name,
            lat = coordinates.lat,
            lon = coordinates.lon
        )
    }
}