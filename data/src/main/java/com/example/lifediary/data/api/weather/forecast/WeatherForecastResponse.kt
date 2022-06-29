package com.example.lifediary.data.api.weather.forecast

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("list")
    val items: List<WeatherForecastItemDto>
)