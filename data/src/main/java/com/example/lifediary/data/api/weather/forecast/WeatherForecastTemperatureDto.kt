package com.example.lifediary.data.api.weather.forecast

import com.google.gson.annotations.SerializedName

data class WeatherForecastTemperatureDto(
    val day: Double,
    val night: Double,
    @SerializedName("morn")
    val morning: Double,
    val min: Double? = null,
    val max: Double? = null
)
