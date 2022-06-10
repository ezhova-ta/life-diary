package com.example.lifediary.utils.dates

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_WITH_YEAR = "dd.MM.yyyy"
private const val DATE_FORMAT_WITHOUT_YEAR = "dd.MM"
private const val TIME_FORMAT_WITH_MILLIS = "HH:mm:ss"
private const val TIME_FORMAT_WITHOUT_MILLIS = "HH:mm"

fun Calendar.toLong(): Long {
	return timeInMillis
}

fun Long.toCalendar(): Calendar {
	return CalendarBuilder(this).build()
}

fun Calendar.getDayOfMonthNumber(): Int {
	return get(Calendar.DATE)
}

fun Calendar.getMonthNumber(): Int {
	return get(Calendar.MONTH) + 1
}

fun Calendar.getYear(): Int {
	return get(Calendar.YEAR)
}

fun Calendar.toTimeString(withMilliseconds: Boolean = false): String {
	val pattern = if(withMilliseconds) TIME_FORMAT_WITH_MILLIS else TIME_FORMAT_WITHOUT_MILLIS
	val format = SimpleDateFormat(pattern, Locale.getDefault())
	return format.format(this.time)
}

fun Calendar.toDateString(withYear: Boolean = true): String {
	val format = SimpleDateFormat(
		if(withYear) DATE_FORMAT_WITH_YEAR else DATE_FORMAT_WITHOUT_YEAR,
		Locale.getDefault()
	)
	return format.format(time)
}

fun Calendar.isDayAfter(date: Calendar): Boolean {
	return timeInMillis > date.timeInMillis && !isSameDay(date)
}

private fun Calendar.isSameDay(date: Calendar): Boolean {
	return getDayOfMonthNumber() == date.getDayOfMonthNumber() &&
		   getMonthNumber() == date.getMonthNumber() &&
		   getYear() == date.getYear()
}

fun Calendar.isWithinInterval(start: Calendar, end: Calendar): Boolean {
	val specifiedStart = CalendarBuilder(start).setHourOfDay(0).setMinutes(0).setSeconds(0).build()
	val specifiedEnd = CalendarBuilder(end).setHourOfDay(23).setMinutes(59).setSeconds(59).build()
	return this in specifiedStart..specifiedEnd
}

fun Calendar.plusDays(amountOfDays: Int): Calendar {
	return (clone() as Calendar).apply { add(Calendar.DATE, amountOfDays) }
}

fun Calendar.isDayOff(): Boolean {
	return get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
		   get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
}