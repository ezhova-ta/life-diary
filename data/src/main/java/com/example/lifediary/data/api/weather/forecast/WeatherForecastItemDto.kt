package com.example.lifediary.data.api.weather.forecast

import com.google.gson.annotations.SerializedName

data class WeatherForecastItemDto(
    @SerializedName("date_epoch")
    val dateInSeconds: Long,
    val day: WeatherForecastDayInfoDto
)