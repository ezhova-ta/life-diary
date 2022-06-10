package com.example.lifediary.utils.dates

import java.util.*

/**
 * Builds a [Calendar][java.util.Calendar] object according to the specified values
 *
 * @param calendar the [Calendar][java.util.Calendar] object to which the specified values will be assigned.
 * If no Calendar object is passed, a new Calendar object with default time zone
 * and locale will be created; this object will be based on the current time
 */
class CalendarBuilder(private val calendar: Calendar = Calendar.getInstance()) {
	/**
	 * Builds a [Calendar][java.util.Calendar] object according to the specified values.
	 *
	 * @param timeInMillis the new time in UTC milliseconds from the epoch;
	 * sets [Calendar's][java.util.Calendar] current time from the given long value
	 */
	constructor(timeInMillis: Long) : this(
		Calendar.getInstance().also { it.timeInMillis = timeInMillis }
	)

	fun setDayOfMonth(value: Int): CalendarBuilder {
		calendar.set(Calendar.DATE, value)
		return this
	}

	fun setMonthNumber(value: Int): CalendarBuilder {
		CalendarBuilder(321)
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