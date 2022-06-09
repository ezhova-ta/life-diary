package com.example.lifediary.data.domain

import com.example.lifediary.utils.CalendarBuilder
import java.util.*

data class MainNote(
	val id: Long? = null,
	var text: String,
	val createdAt: Calendar = CalendarBuilder().build()
)
