package com.example.lifediary.data.api.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("weather")
    val descriptions: List<WeatherDescriptionDto>,
    @SerializedName("main")
    val parameters: WeatherParametersDto,
    val wind: WeatherWindDto
)