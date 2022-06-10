package com.example.lifediary.utils.dates

import com.example.lifediary.data.domain.Day
import com.example.lifediary.utils.dates.DatesConverter.dayToCalendar
import com.kizitonwose.calendarview.model.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_TIME_FORMAT_WITH_MILLIS = "dd.MM.yyyy HH:mm:ss"
private const val DATE_TIME_FORMAT_WITHOUT_MILLIS = "dd.MM.yyyy HH:mm"

fun Day.toDateString(withYear: Boolean = true): String {
	return dayToCalendar(this).toDateString(withYear)
}

fun Day.toDateTimeString(withMilliseconds: Boolean = false): String {
	val pattern = if(withMilliseconds) DATE_TIME_FORMAT_WITH_MILLIS else DATE_TIME_FORMAT_WITHOUT_MILLIS
	val format = SimpleDateFormat(pattern, Locale.getDefault())
	return format.format(dayToCalendar(this).time)
}

fun Day.isSameDay(date: CalendarDay): Boolean {
	return dayNumber == date.date.dayOfMonth &&
		   monthNumber == date.date.monthValue &&
		   year == date.date.year
}

fun Day.isSameDay(dateInSeconds: Long): Boolean {
	val dateInMillis = dateInSeconds * 1000
	val calendar = CalendarBuilder(dateInMillis).build()

	return dayNumber == calendar.getDayOfMonthNumber() &&
		   monthNumber == calendar.getMonthNumber() &&
		   year == calendar.getYear()
}

fun Day.isWithinInterval(start: Calendar, end: Calendar): Boolean {
	return dayToCalendar(this).isWithinInterval(start, end)
}