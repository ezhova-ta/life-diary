package com.example.lifediary.domain.models

import com.example.lifediary.BuildConfig

data class WeatherDescription(
    val shortDescription: String,
    val description: String,
    val icon: String
) {
	val iconUrl = "${BuildConfig.WEATHER_API_ICON_URL}$icon@2x.png"
}