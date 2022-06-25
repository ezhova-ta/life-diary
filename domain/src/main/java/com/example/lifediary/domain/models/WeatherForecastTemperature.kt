package com.example.lifediary.domain.models

data class WeatherForecastTemperature(
    val day: Int,
    val night: Int,
    val morning: Int,
    val min: Int? = null,
    val max: Int? = null
)
