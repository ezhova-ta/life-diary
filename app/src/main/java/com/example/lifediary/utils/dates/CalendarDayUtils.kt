package com.example.lifediary.utils.dates

import com.example.lifediary.data.domain.Day
import com.example.lifediary.data.domain.MemorableDate
import com.kizitonwose.calendarview.model.CalendarDay
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun CalendarDay.toDomain(): Day {
	return Day(date.dayOfMonth, date.monthValue, date.year)
}

fun CalendarDay.isToday(): Boolean {
	val today = LocalDate.now(ZoneId.systemDefault())
	return date.isEqual(today)
}

fun CalendarDay.isSameDayInYear(memorableDate: MemorableDate): Boolean {
	return date.dayOfMonth == memorableDate.dayNumber && date.monthValue == memorableDate.monthNumber
}

fun CalendarDay.isWithinInterval(start: Calendar, end: Calendar): Boolean {
	return CalendarBuilder()
		.setDayOfMonth(date.dayOfMonth)
		.setMonthNumber(date.monthValue)
		.setYearNumber(date.year)
		.build()
		.isWithinInterval(start, end)
}