package com.example.lifediary.data.api.weather.forecast

import com.example.lifediary.data.domain.WeatherForecastTemperature
import com.google.gson.annotations.SerializedName

data class WeatherForecastTemperatureDto(
    val day: Double,
    val night: Double,
    @SerializedName("morn")
    val morning: Double,
    val min: Double? = null,
    val max: Double? = null
) {
    fun toDomain(): WeatherForecastTemperature {
        return WeatherForecastTemperature(
            day = day.toInt(),
            night = night.toInt(),
            morning = morning.toInt(),
            min = min?.toInt(),
            max = max?.toInt()
        )
    }
}
