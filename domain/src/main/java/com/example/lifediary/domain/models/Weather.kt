package com.example.lifediary.domain.models

data class Weather(
    val id: Long? = null,
    val shortDescription: String?,
    val description: String?,
    val icon: String?,
    val temperature: Int,
    val temperatureFeelsLike: Int,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val gustSpeed: Double
)
