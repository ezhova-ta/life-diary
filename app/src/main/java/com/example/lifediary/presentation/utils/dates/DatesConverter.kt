package com.example.lifediary.presentation.utils.dates

import com.example.lifediary.domain.models.Day
import java.util.*

object DatesConverter {
	fun dayToCalendar(day: Day): Calendar {
		return CalendarBuilder()
			.setDayOfMonth(day.dayNumber)
			.setMonthNumber(day.monthNumber)
			.setYearNumber(day.year)
			.build()
	}

	fun calendarToDay(calendar: Calendar): Day {
		return Day(
			calendar.getDayOfMonthNumber(),
			calendar.getMonthNumber(),
			calendar.getYear()
		)
	}
}