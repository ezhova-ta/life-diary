package com.example.lifediary.data.api.weather

import com.google.gson.annotations.SerializedName

data class WeatherParametersDto(
    @SerializedName("temp_c")
    val temperature: Double,
    @SerializedName("feelslike_c")
    val temperatureFeelsLike: Double,
    val humidity: Int,
    @SerializedName("wind_kph")
    val windSpeedKmPerHour: Double,
    val condition: WeatherConditionDto
)