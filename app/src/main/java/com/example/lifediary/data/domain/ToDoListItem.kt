package com.example.lifediary.data.domain

import java.util.*

data class ToDoListItem(
	val id: Long? = null,
	var text: String,
	val day: Day,
	var isDone: Boolean = false,
	var notificationEnabled: Boolean = false,
	var notificationTime: Calendar = Calendar.getInstance(),
	val createdAt: Calendar = Calendar.getInstance()
)
