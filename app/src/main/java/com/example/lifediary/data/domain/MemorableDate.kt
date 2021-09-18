package com.example.lifediary.data.domain

import com.example.lifediary.utils.CalendarEvent

data class MemorableDate(
	val id: Long? = null,
	override var name: String,
	override var dayNumber: Int,
	override var monthNumber: Int,
	override var year: Int? = null
) : CalendarEvent()
