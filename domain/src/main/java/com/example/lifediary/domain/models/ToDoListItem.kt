package com.example.lifediary.domain.models

import com.example.lifediary.domain.utils.CalendarBuilder
import java.util.*

data class ToDoListItem(
	val id: Long? = null,
	var text: String,
	val day: Day,
	var isDone: Boolean = false,
	var notificationEnabled: Boolean = false,
	var notificationTime: Calendar = CalendarBuilder().build(),
	val createdAt: Calendar = CalendarBuilder().build()
)
