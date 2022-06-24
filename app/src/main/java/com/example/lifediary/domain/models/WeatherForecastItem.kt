package com.example.lifediary.domain.models

data class WeatherForecastItem(
    val dateInSeconds: Long,
    val temperature: WeatherForecastTemperature,
    val temperatureFeelsLike: WeatherForecastTemperature,
    val pressure: Int,
    val humidity: Int,
    val weather: List<WeatherDescription>,
    val windSpeed: Double,
    val gustSpeed: Double,
    val clouds: Double,
    val rain: Double
)