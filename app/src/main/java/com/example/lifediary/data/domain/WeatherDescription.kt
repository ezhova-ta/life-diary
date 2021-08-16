package com.example.lifediary.data.domain

import com.example.lifediary.BuildConfig

data class WeatherDescription(
    val shortDescription: String,
    val description: String,
    val icon: String
) {
	val iconUrl = String.format("%s%s@2x.png", BuildConfig.WEATHER_API_ICON_URL, icon)
}