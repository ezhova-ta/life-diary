package com.example.lifediary.data.api.weather.forecast

import com.example.lifediary.data.api.weather.WeatherDescriptionDto
import com.example.lifediary.data.domain.WeatherDescription
import com.example.lifediary.data.domain.WeatherForecastItem
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
) {
    fun toDomain(): WeatherForecastItem {
        return WeatherForecastItem(
            dateInSeconds = dateInSeconds,
            temperature = temperature.toDomain(),
            temperatureFeelsLike = temperatureFeelsLike.toDomain(),
            pressure = pressure,
            humidity = humidity,
            weather = weather.toDomain(),
            windSpeed = windSpeed,
            gustSpeed = gustSpeed,
            clouds = clouds,
            rain = rain
        )
    }

    private fun List<WeatherDescriptionDto>.toDomain(): List<WeatherDescription> {
        return map { it.toDomain() }
    }
}