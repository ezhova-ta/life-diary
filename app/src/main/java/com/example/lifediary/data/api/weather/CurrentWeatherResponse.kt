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
    companion object {
        fun fromDomain(weather: Weather): CurrentWeatherResponse {
            return CurrentWeatherResponse(
                descriptions = weather.getWeatherDescriptionDtoList(),
                parameters = WeatherParametersDto(
                    weather.temperature,
                    weather.temperatureFeelsLike,
                    weather.pressure,
                    weather.humidity
                ),
                wind = WeatherWindDto(
                    weather.windSpeed,
                    weather.gustSpeed
                )
            )
        }

        private fun Weather.getWeatherDescriptionDtoList() =
            if(shortDescription == null || description == null || icon == null) {
                emptyList()
            } else {
                listOf(WeatherDescriptionDto(shortDescription, description, icon))
            }
    }

    fun toDomain(): Weather {
        return Weather(
            shortDescription = descriptions.firstOrNull()?.shortDescription,
            description = descriptions.firstOrNull()?.description,
            icon = descriptions.firstOrNull()?.icon,
            temperature = parameters.temperature,
            temperatureFeelsLike = parameters.temperatureFeelsLike,
            pressure = parameters.pressure,
            humidity = parameters.humidity,
            windSpeed = wind.speed,
            gustSpeed = wind.gust
        )
    }
}