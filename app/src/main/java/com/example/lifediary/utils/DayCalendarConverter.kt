package com.example.lifediary.utils

import com.example.lifediary.data.domain.Day
import java.util.*

object DayCalendarConverter {
	fun dayToCalendar(day: Day): Calendar {
		return CalendarBuilder()
			.setDayOfMonth(day.dayNumber)
			.setMonthNumber(day.monthNumber)
			.setYearNumber(day.year)
			.build()
	}

	fun calendarToDay(calendar: Calendar): Day {
		return Day(
			calendar.get(Calendar.DATE),
			calendar.get(Calendar.MONTH) + 1,
			calendar.get(Calendar.YEAR)
		)
	}
}