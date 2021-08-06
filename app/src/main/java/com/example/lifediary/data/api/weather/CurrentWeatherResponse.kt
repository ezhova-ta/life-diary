package com.example.lifediary.data.api.weather

import com.example.lifediary.data.domain.Weather
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("weather")
    val descriptions: List<WeatherDescriptionDto>,
    @SerializedName("main")
    val parameters: WeatherParametersDto,
    val wind: WeatherWindDto
) {
    fun toDomain(): Weather {
        return Weather(
            shortDescription = descriptions.firstOrNull()?.shortDescription,
            description = descriptions.firstOrNull()?.description,
            icon = descriptions.firstOrNull()?.icon,
            temperature = parameters.temperature.toInt(),
            temperatureFeelsLike = parameters.temperatureFeelsLike.toInt(),
            pressure = parameters.pressure,
            humidity = parameters.humidity,
            windSpeed = wind.speed,
            gustSpeed = wind.gust
        )
    }
}