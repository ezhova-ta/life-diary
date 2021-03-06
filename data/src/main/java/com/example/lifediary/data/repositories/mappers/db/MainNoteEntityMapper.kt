package com.example.lifediary.data.repositories.mappers.db

import com.example.lifediary.data.db.models.MainNoteEntity
import com.example.lifediary.domain.models.MainNote

object MainNoteEntityMapper : EntityMapper<MainNoteEntity, MainNote> {
	override fun MainNoteEntity.toDomain(): MainNote {
		return MainNote(
			id = id,
			text = text,
			createdAt = createdAt
		)
	}

	override fun MainNote.toEntity(): MainNoteEntity {
		return MainNoteEntity(
			id = id,
			text = text,
			createdAt = createdAt
		)
	}
}