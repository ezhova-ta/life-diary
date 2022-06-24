package com.example.lifediary.data.repositories.mappers

import com.example.lifediary.data.db.entities.DateNoteEntity
import com.example.lifediary.domain.models.DateNote
import com.example.lifediary.domain.models.Day

object DateNoteEntityMapper : EntityMapper<DateNoteEntity, DateNote> {
	override fun DateNoteEntity.toDomain(): DateNote {
		return DateNote(
			id = id,
			text = text,
			day = Day(dayNumber, monthNumber, year)
		)
	}

	override fun DateNote.toEntity(): DateNoteEntity {
		return DateNoteEntity(
			id = id,
			text = text,
			dayNumber = day.dayNumber,
			monthNumber = day.monthNumber,
			year = day.year
		)
	}
}