package com.example.lifediary.domain.models

import com.example.lifediary.BuildConfig
import com.example.lifediary.presentation.utils.createStringWithPlusOrMinusSign

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
) {
    val iconUrl = "${BuildConfig.WEATHER_API_ICON_URL}$icon@2x.png"
    val temperatureString = temperature.createStringWithPlusOrMinusSign()
    val temperatureFeelsLikeString = temperatureFeelsLike.createStringWithPlusOrMinusSign()
}
