package com.example.lifediary.data.repositories.mappers.db

import com.example.lifediary.data.db.models.MemorableDateEntity
import com.example.lifediary.domain.models.MemorableDate

object MemorableDateEntityMapper : EntityMapper<MemorableDateEntity, MemorableDate> {
	override fun MemorableDateEntity.toDomain(): MemorableDate {
		return MemorableDate(
			id = id,
			name = name,
			dayNumber = dayNumber,
			monthNumber = monthNumber,
			year = year
		)
	}

	override fun MemorableDate.toEntity(): MemorableDateEntity {
		return MemorableDateEntity(
			id = id,
			name = name,
			dayNumber = dayNumber,
			monthNumber = monthNumber,
			year = year
		)
	}
}