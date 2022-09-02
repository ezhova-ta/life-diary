package com.example.lifediary.data.api.weather.forecast

import com.example.lifediary.data.api.weather.WeatherConditionDto
import com.google.gson.annotations.SerializedName

data class WeatherForecastDayInfoDto(
	@SerializedName("maxtemp_c")
	val maxTemperature: Double,
	@SerializedName("mintemp_c")
	val minTemperature: Double,
	val condition: WeatherConditionDto
)
