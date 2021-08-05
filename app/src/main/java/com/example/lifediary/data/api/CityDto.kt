package com.example.lifediary.data.api

import com.example.lifediary.data.domain.City
import com.google.gson.annotations.SerializedName

data class CityDto(
    val id: Long?,
    val name: String,
    @SerializedName("coord")
    val coordinates: CoordinatesDto
) {
    companion object {
        fun fromDomain(city: City): CityDto {
            return CityDto(
                id = city.id,
                name = city.name,
                coordinates = CoordinatesDto(city.lat, city.lon)
            )
        }
    }

    fun toDomain(): City {
        return City(
            id = id,
            name = name,
            lat = coordinates.lat,
            lon = coordinates.lon
        )
    }
}