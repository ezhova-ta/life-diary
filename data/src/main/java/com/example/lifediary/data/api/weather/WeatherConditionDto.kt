package com.example.lifediary.data.api.weather

import com.google.gson.annotations.SerializedName

data class WeatherConditionDto(
	val text: String,
	@SerializedName("icon")
	val iconUrl: String
)
