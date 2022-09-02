package com.example.lifediary.domain.models

data class Weather(
    val id: Long? = null,
    val iconUrl: String?,
    val temperature: Int,
    val temperatureFeelsLike: Int,
    val humidity: Int,
    val windSpeedMetersPerSecond: Double,
    val text: String?
)
