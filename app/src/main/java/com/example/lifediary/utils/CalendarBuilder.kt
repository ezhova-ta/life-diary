package com.example.lifediary.utils

import java.util.*

/**
 * Builds a Calendar object according to the specified values
 *
 * @param calendar The Calendar object to which the specified values will be assigned.
 * If no Calendar object is passed, a new Calendar object with default time zone
 * and locale will be created. This object will be based on the current time
 */
class CalendarBuilder(private val calendar: Calendar = Calendar.getInstance()) {
	fun setDayOfMonth(value: Int): CalendarBuilder {
		calendar.set(Calendar.DATE, value)
		return this
	}

	fun setMonthNumber(value: Int): CalendarBuilder {
		calendar.set(Calendar.MONTH, value - 1)
		return this
	}

	fun setYearNumber(value: Int): CalendarBuilder {
		calendar.set(Calendar.YEAR, value)
		return this
	}

	fun setHourOfDay(value: Int): CalendarBuilder {
		calendar.set(Calendar.HOUR_OF_DAY, value)
		return this
	}

	fun setMinutes(value: Int): CalendarBuilder {
		calendar.set(Calendar.MINUTE, value)
		return this
	}

	fun setSeconds(value: Int): CalendarBuilder {
		calendar.set(Calendar.SECOND, value)
		return this
	}

	fun build(): Calendar {
		return calendar
	}
}