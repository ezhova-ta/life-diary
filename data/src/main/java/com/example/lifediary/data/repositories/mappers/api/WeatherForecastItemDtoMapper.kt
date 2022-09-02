package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.forecast.WeatherForecastItemDto
import com.example.lifediary.domain.models.WeatherForecastItem

object WeatherForecastItemDtoMapper : DtoMapper<WeatherForecastItemDto, WeatherForecastItem> {
	override fun WeatherForecastItemDto.toDomain(): WeatherForecastItem {
		return WeatherForecastItem(
			dateInSeconds = dateInSeconds,
			maxTemperature = day.maxTemperature.toInt(),
			minTemperature = day.minTemperature.toInt(),
			text = day.condition.text,
			iconUrl = day.condition.iconUrl
		)
	}
}