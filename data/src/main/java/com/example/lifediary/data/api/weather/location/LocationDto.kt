package com.example.lifediary.data.api.weather.location

data class LocationDto(
    val id: Long?,
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double
)