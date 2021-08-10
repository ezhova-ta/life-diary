package com.example.lifediary.data.domain

import com.example.lifediary.BuildConfig

data class Weather(
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
    fun getIconUrl() =
        String.format("%s%s@2x.png", BuildConfig.WEATHER_API_ICON_URL, icon)

    fun getTemperatureString(): String {
        return temperature.createStringWithPlusOrMinusSign()
    }

    fun getTemperatureFeelsLikeString(): String {
        return temperatureFeelsLike.createStringWithPlusOrMinusSign()
    }

    private fun Int.createStringWithPlusOrMinusSign(): String {
        if(this < 0) return toString()
        return String.format("+%d", this)
    }
}
