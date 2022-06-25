package com.example.lifediary.data.api.weather.location

import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    val count: Int,
    val message: String,
    @SerializedName("cod")
    val code: Int,
    @SerializedName("list")
    val locations: List<LocationDto>
)