package com.example.lifediary.data.repositories.mappers.db

import com.example.lifediary.data.db.models.LocationEntity
import com.example.lifediary.domain.models.Location

object LocationEntityMapper : EntityMapper<LocationEntity, Location> {
	override fun LocationEntity.toDomain(): Location {
		return Location(
			id = id,
			name = name,
			lat = lat,
			lon = lon
		)
	}

	override fun Location.toEntity(): LocationEntity {
		return LocationEntity(
			id = id,
			name = name,
			lat = lat,
			lon = lon
		)
	}
}