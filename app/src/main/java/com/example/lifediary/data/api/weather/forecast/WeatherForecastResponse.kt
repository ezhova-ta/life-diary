package com.example.lifediary.data.api.weather.forecast

import com.example.lifediary.data.domain.WeatherForecast
import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("list")
    val items: List<WeatherForecastItemDto>
) {
    fun toDomain(): WeatherForecast {
        return WeatherForecast(
            items = items.map { it.toDomain() }
        )
    }
}