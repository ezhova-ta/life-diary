package com.example.lifediary.data.repositories.mappers

import com.example.lifediary.data.db.models.PostAddressEntity
import com.example.lifediary.domain.models.PostAddress

object PostAddressEntityMapper : EntityMapper<PostAddressEntity, PostAddress> {
	override fun PostAddressEntity.toDomain(): PostAddress {
		return PostAddress(
			id = id,
			name = name,
			street = street,
			buildingNumber = buildingNumber,
			apartmentNumber = apartmentNumber,
			city = city,
			postcode = postcode,
			edgeRegion = edgeRegion,
			createdAt = createdAt
		)
	}

	override fun PostAddress.toEntity(): PostAddressEntity {
		return PostAddressEntity(
			id = id,
			name = name,
			street = street,
			buildingNumber = buildingNumber,
			apartmentNumber = apartmentNumber,
			city = city,
			postcode = postcode,
			edgeRegion = edgeRegion,
			createdAt = createdAt
		)
	}
}