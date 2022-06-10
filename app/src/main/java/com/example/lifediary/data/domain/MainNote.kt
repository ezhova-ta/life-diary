package com.example.lifediary.data.domain

import com.example.lifediary.utils.dates.CalendarBuilder
import java.util.*

data class MainNote(
	val id: Long? = null,
	var text: String,
	val createdAt: Calendar = CalendarBuilder().build()
)
