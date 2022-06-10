package com.example.lifediary.data.domain

import com.example.lifediary.utils.dates.CalendarBuilder
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
