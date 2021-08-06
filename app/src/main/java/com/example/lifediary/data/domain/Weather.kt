package com.example.lifediary.data.domain

data class Weather(
    val shortDescription: String?,
    val description: String?,
    val icon: String?,
    val temperature: Double,
    val temperatureFeelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val gustSpeed: Double
)
