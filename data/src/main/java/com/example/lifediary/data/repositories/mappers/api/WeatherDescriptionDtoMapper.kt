package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.WeatherDescriptionDto
import com.example.lifediary.domain.models.WeatherDescription

object WeatherDescriptionDtoMapper : DtoMapper<WeatherDescriptionDto, WeatherDescription> {
	override fun WeatherDescriptionDto.toDomain(): WeatherDescription {
		return WeatherDescription(
			shortDescription = shortDescription,
			description = description,
			icon = icon
		)
	}
}