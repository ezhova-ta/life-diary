package com.example.lifediary.data.api.weather.location

import com.google.gson.annotations.SerializedName

data class LocationDto(
    val id: Long?,
    val name: String,
    @SerializedName("coord")
    val coordinates: CoordinatesDto
)