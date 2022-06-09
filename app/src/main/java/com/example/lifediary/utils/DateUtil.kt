package com.example.lifediary.utils

import com.example.lifediary.data.domain.Day
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.utils.DayCalendarConverter.dayToCalendar
import com.kizitonwose.calendarview.model.CalendarDay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

private const val DATE_FORMAT_WITH_YEAR = "dd.MM.yyyy"
private const val DATE_FORMAT_WITHOUT_YEAR = "dd.MM"
private const val DATE_TIME_FORMAT_WITH_MILLIS = "dd.MM.yyyy HH:mm:ss"
private const val DATE_TIME_FORMAT_WITHOUT_MILLIS = "dd.MM.yyyy HH:mm"
private const val TIME_FORMAT_WITH_MILLIS = "HH:mm:ss"
private const val TIME_FORMAT_WITHOUT_MILLIS = "HH:mm"

fun Calendar.toLong(): Long {
    return timeInMillis
}

fun Long.toCalendar(): Calendar {
    return CalendarBuilder(this).build()
}

fun Day.toDateString(withYear: Boolean = true): String {
    return dayToCalendar(this).toDateString(withYear)
}

fun Day.toDateTimeString(withMilliseconds: Boolean = false): String {
    val pattern = if(withMilliseconds) DATE_TIME_FORMAT_WITH_MILLIS else DATE_TIME_FORMAT_WITHOUT_MILLIS
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(dayToCalendar(this).time)
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

fun getDateString(dayNumber: Int, monthNumber: Int, year: Int? = null): String {
    return if(year == null) {
        val thisYear = CalendarBuilder().build().get(Calendar.YEAR)
        val day = Day(dayNumber, monthNumber, thisYear)
        day.toDateString(false)
    } else {
        val day = Day(dayNumber, monthNumber, year)
        day.toDateString()
    }
}

fun Day.isSameDay(date: CalendarDay): Boolean {
    return dayNumber == date.date.dayOfMonth &&
        monthNumber == date.date.monthValue &&
        year == date.date.year
}

fun Day.isSameDay(dateInSeconds: Long): Boolean {
    val dateInMillis = dateInSeconds * 1000
    val date = CalendarBuilder(dateInMillis).build()

    return dayNumber == date.get(Calendar.DATE) &&
        monthNumber == date.getMonthNumber() &&
        year == date.get(Calendar.YEAR)
}

private fun Calendar.getMonthNumber(): Int {
    return get(Calendar.MONTH) + 1
}

fun Calendar.isSameDay(date: Calendar): Boolean {
    return get(Calendar.DATE) == date.get(Calendar.DATE) &&
           get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
           get(Calendar.YEAR) == date.get(Calendar.YEAR)
}

fun Calendar.isDayAfter(date: Calendar): Boolean {
    return timeInMillis > date.timeInMillis && !isSameDay(date)
}

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
    val calendar = CalendarBuilder()
        .setDayOfMonth(date.dayOfMonth)
        .setMonthNumber(date.monthValue)
        .setYearNumber(date.year)
        .build()
    return calendar.isWithinInterval(start, end)
}

fun Day.isWithinInterval(start: Calendar, end: Calendar): Boolean {
    return dayToCalendar(this).isWithinInterval(start, end)
}

private fun Calendar.isWithinInterval(start: Calendar, end: Calendar): Boolean {
    val specifiedStart = CalendarBuilder(start).setHourOfDay(0).setMinutes(0).setSeconds(0).build()
    val specifiedEnd = CalendarBuilder(end).setHourOfDay(23).setMinutes(59).setSeconds(59).build()
    return this in specifiedStart..specifiedEnd
}

fun Calendar.plusDays(amountOfDays: Int): Calendar {
    return (clone() as Calendar).apply { add(Calendar.DATE, amountOfDays) }
}

/**
 * Calculates the amount of time between two dates.
 * The result will be negative if the end is before the start
 *
 * @param date1 [Calendar][java.util.Calendar] object
 * @param date2 other [Calendar][java.util.Calendar] object
 * @return the amount of days between date1 (inclusive) and date2 (exclusive).
 * Positive if date2 is later than date1, negative if earlier
 */
fun getDaysBetween(date1: Calendar, date2: Calendar): Long {
    return ChronoUnit.DAYS.between(date1.toInstant(), date2.toInstant())
}

fun Calendar.isDayOff(): Boolean =
    get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY

fun MemorableDate.isToday(): Boolean {
    val nowDayNumber = getNowDayNumber()
    val nowMonthNumber = getNowMonthNumber()
    return dayNumber == nowDayNumber && monthNumber == nowMonthNumber
}

fun getNowDayNumber(): Int {
    return CalendarBuilder().build().get(Calendar.DATE)
}

fun getNowMonthNumber(): Int {
    return CalendarBuilder().build().get(Calendar.MONTH) + 1
}