package com.example.lifediary.data.repositories.mappers.api

import com.example.lifediary.data.api.weather.location.LocationDto
import com.example.lifediary.domain.models.Location

object LocationDtoMapper : DtoMapper<LocationDto, Location> {
	override fun LocationDto.toDomain(): Location {
		return Location(
			id = id,
			name = name,
			lat = coordinates.lat,
			lon = coordinates.lon
		)
	}
}