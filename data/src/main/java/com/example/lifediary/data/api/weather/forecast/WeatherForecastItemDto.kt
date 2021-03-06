package com.example.lifediary.data.api.weather.forecast

import com.example.lifediary.data.api.weather.WeatherDescriptionDto
import com.example.lifediary.data.repositories.mappers.api.WeatherDescriptionDtoMapper.toDomain
import com.example.lifediary.domain.models.WeatherDescription
import com.example.lifediary.domain.models.WeatherForecastItem
import com.google.gson.annotations.SerializedName

data class WeatherForecastItemDto(
    @SerializedName("dt")
    val dateInSeconds: Long,
    @SerializedName("temp")
    val temperature: WeatherForecastTemperatureDto,
    @SerializedName("feels_like")
    val temperatureFeelsLike: WeatherForecastTemperatureDto,
    val pressure: Int,
    val humidity: Int,
    val weather: List<WeatherDescriptionDto>,
    @SerializedName("speed")
    val windSpeed: Double,
    @SerializedName("gust")
    val gustSpeed: Double,
    val clouds: Double,
    val rain: Double
)