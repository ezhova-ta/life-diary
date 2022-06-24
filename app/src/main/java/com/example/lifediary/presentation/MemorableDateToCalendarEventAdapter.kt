package com.example.lifediary.presentation

import com.example.lifediary.domain.models.MemorableDate

class MemorableDateToCalendarEventAdapter(private val memorableDate: MemorableDate) : CalendarEvent() {
	override val name: String
		get() = memorableDate.name
	override val dayNumber: Int
		get() = memorableDate.dayNumber
	override val monthNumber: Int
		get() = memorableDate.monthNumber
	override val year: Int?
		get() = memorableDate.year

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as MemorableDateToCalendarEventAdapter

		if (name != other.name) return false
		if (dayNumber != other.dayNumber) return false
		if (monthNumber != other.monthNumber) return false
		if (year != other.year) return false

		return true
	}

	override fun hashCode(): Int {
		var result = name.hashCode()
		result = 31 * result + name.hashCode()
		result = 31 * result + dayNumber
		result = 31 * result + monthNumber
		result = 31 * result + (year ?: 0)
		return result
	}
}

fun List<MemorableDate>.toCalendarEventList(): List<CalendarEvent> {
	return map { it.toCalendarEvent() }
}

private fun MemorableDate.toCalendarEvent(): CalendarEvent {
	return MemorableDateToCalendarEventAdapter(this)
}