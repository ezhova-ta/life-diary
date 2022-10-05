package com.example.lifediary.domain.models

import com.example.lifediary.domain.utils.CalendarBuilder
import java.util.*

data class MainNote(
	var id: Long? = null,
	var text: String,
	val createdAt: Calendar = CalendarBuilder().build()
)
