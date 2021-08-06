package com.example.lifediary.data.api.weather

import com.google.gson.annotations.SerializedName

data class WeatherParametersDto(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val temperatureFeelsLike: Double,
    val pressure: Int,
    val humidity: Int
)