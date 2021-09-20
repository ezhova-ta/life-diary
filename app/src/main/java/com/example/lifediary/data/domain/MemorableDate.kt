package com.example.lifediary.data.domain

import com.example.lifediary.utils.CalendarEvent
import java.util.*

data class MemorableDate(
	val id: Long? = null,
	override var name: String,
	override var dayNumber: Int,
	override var monthNumber: Int,
	override var year: Int? = null
) : CalendarEvent() {
	fun howManyYearsAgo(): Int? {
		val year = year ?: return null
		val thisYear = Calendar.getInstance().get(Calendar.YEAR)
		val diffYears = thisYear - year
		if(diffYears <= 0) return null
		return thisYear - year
	}
}
