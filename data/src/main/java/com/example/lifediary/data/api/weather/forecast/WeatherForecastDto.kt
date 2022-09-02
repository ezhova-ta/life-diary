package com.example.lifediary.data.api.weather.forecast

import com.google.gson.annotations.SerializedName

data class WeatherForecastDto(
	@SerializedName("forecastday")
	val items: List<WeatherForecastItemDto>
)
