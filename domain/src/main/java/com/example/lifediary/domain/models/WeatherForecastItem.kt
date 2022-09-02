package com.example.lifediary.domain.models

data class WeatherForecastItem(
    val dateInSeconds: Long,
    val maxTemperature: Int,
    val minTemperature: Int,
    val text: String,
    val iconUrl: String
)