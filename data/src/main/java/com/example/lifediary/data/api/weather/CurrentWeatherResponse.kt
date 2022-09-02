package com.example.lifediary.data.api.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val parameters: WeatherParametersDto
)