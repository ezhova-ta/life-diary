package com.example.lifediary.domain.models

import com.example.lifediary.presentation.utils.dates.CalendarBuilder
import java.util.*

data class MainNote(
	val id: Long? = null,
	var text: String,
	val createdAt: Calendar = CalendarBuilder().build()
)
