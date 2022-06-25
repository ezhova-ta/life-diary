package com.example.lifediary.data.repositories.mappers

import com.example.lifediary.data.db.models.MenstruationPeriodEntity
import com.example.lifediary.domain.models.MenstruationPeriod

object MenstruationPeriodEntityMapper : EntityMapper<MenstruationPeriodEntity, MenstruationPeriod> {
	override fun MenstruationPeriodEntity.toDomain(): MenstruationPeriod {
		return MenstruationPeriod(
			id = id,
			startDate = startDate,
			endDate = endDate
		)
	}

	override fun MenstruationPeriod.toEntity(): MenstruationPeriodEntity {
		return MenstruationPeriodEntity(
			id = id,
			startDate = startDate,
			endDate = endDate
		)
	}
}