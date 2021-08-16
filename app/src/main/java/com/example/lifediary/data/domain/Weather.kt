package com.example.lifediary.data.domain

import com.example.lifediary.BuildConfig
import com.example.lifediary.utils.createStringWithPlusOrMinusSign

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
    val iconUrl = String.format("%s%s@2x.png", BuildConfig.WEATHER_API_ICON_URL, icon)
    val temperatureString = temperature.createStringWithPlusOrMinusSign()
    val temperatureFeelsLikeString = temperatureFeelsLike.createStringWithPlusOrMinusSign()
}
