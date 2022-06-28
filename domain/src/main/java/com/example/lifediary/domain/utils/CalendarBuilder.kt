package com.example.lifediary.domain.utils

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
		return apply { calendar.set(Calendar.DATE, value) }
	}

	fun setMonthNumber(value: Int): CalendarBuilder {
		return apply { calendar.set(Calendar.MONTH, value - 1) }
	}

	fun setYearNumber(value: Int): CalendarBuilder {
		return apply { calendar.set(Calendar.YEAR, value) }
	}

	fun setHourOfDay(value: Int): CalendarBuilder {
		return apply { calendar.set(Calendar.HOUR_OF_DAY, value) }
	}

	fun setMinutes(value: Int): CalendarBuilder {
		return apply { calendar.set(Calendar.MINUTE, value) }
	}

	fun setSeconds(value: Int): CalendarBuilder {
		return apply { calendar.set(Calendar.SECOND, value) }
	}

	fun build(): Calendar {
		return calendar
	}
}