package com.example.lifediary.data.repositories.mappers

import com.example.lifediary.data.db.entities.ToDoListItemEntity
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.ToDoListItem

object ToDoListItemEntityMapper : EntityMapper<ToDoListItemEntity, ToDoListItem> {
	override fun ToDoListItemEntity.toDomain(): ToDoListItem {
		return ToDoListItem(
			id = id,
			text = text,
			day = Day(dayNumber, monthNumber, year),
			isDone = isDone,
			notificationEnabled = notificationEnabled,
			notificationTime = notificationTime,
			createdAt = createdAt
		)
	}

	override fun ToDoListItem.toEntity(): ToDoListItemEntity {
		return ToDoListItemEntity(
			id = id,
			text = text,
			dayNumber = day.dayNumber,
			monthNumber = day.monthNumber,
			year = day.year,
			isDone = isDone,
			notificationEnabled = notificationEnabled,
			notificationTime = notificationTime,
			createdAt = createdAt
		)
	}
}