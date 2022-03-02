package com.example.lifediary.utils

import com.example.lifediary.data.domain.MemorableDate
import com.kizitonwose.calendarview.model.CalendarDay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
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
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar
}

fun Day.toDateString(withYear: Boolean = true): String {
    return toCalendar().toDateString(withYear)
}

fun Day.toDateTimeString(withMilliseconds: Boolean = false): String {
    val pattern = if(withMilliseconds) DATE_TIME_FORMAT_WITH_MILLIS else DATE_TIME_FORMAT_WITHOUT_MILLIS
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this.toCalendar().time)
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
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        val day = Day(dayNumber, monthNumber, thisYear)
        day.toDateString(false)
    } else {
        val day = Day(dayNumber, monthNumber, year)
        day.toDateString()
    }
}

fun Day.toCalendar(): Calendar {
    return createCalendarInstance(dayNumber, monthNumber, year)
}

fun Day.isSameDay(date: CalendarDay): Boolean {
    return dayNumber == date.date.dayOfMonth &&
        monthNumber == date.date.monthValue &&
        year == date.date.year
}

fun Day.isSameDay(dateInSeconds: Long): Boolean {
    val dateInMillis = dateInSeconds * 1000
    val date = Calendar.getInstance().apply {
        timeInMillis = dateInMillis
    }

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

fun Calendar.toDomain(): Day {
    return Day(
        get(Calendar.DATE),
        get(Calendar.MONTH) + 1,
        get(Calendar.YEAR)
    )
}

fun CalendarDay.isToday(): Boolean {
    val today = LocalDate.now(ZoneId.systemDefault())
    return date.isEqual(today)
}

fun CalendarDay.isSameDayInYear(memorableDate: MemorableDate): Boolean {
    return date.dayOfMonth == memorableDate.dayNumber && date.monthValue == memorableDate.monthNumber
}

fun CalendarDay.isWithinInterval(start: Calendar, end: Calendar): Boolean {
    val timeInMillis = createCalendarInstance(date.dayOfMonth, date.monthValue, date.year)
    val startTimeInMillis = start.specify(hourOfDay = 0, minutes = 0, seconds = 0)
    val endTimeInMillis = end.specify(hourOfDay = 23, minutes = 59, seconds = 59)
    return timeInMillis in startTimeInMillis..endTimeInMillis
}

/**
 * Gets a calendar using the default time zone and locale
 *
 * @param dayOfMonth Day number of the month. The first day of the month has value 1
 * @param month Month number. The first month has value 1
 * @param year Year
 * @param hourOfDay Hour of the day. Used for the 24-hour clock. E.g., at 10:04:15.250 PM the hourOfDay is 22
 * @param minutes Minutes within the hour. E.g., at 10:04:15.250 PM the minutes is 4
 * @param seconds Second within the minute. E.g., at 10:04:15.250 PM the seconds is 15
 */
fun createCalendarInstance(
    dayOfMonth: Int? = null,
    month: Int? = null,
    year: Int? = null,
    hourOfDay: Int? = null,
    minutes: Int? = null,
    seconds: Int? = null
): Calendar {
    return Calendar.getInstance().specify(dayOfMonth, month, year, hourOfDay, minutes, seconds)
}

/**
 * Sets the given values for the calendar
 *
 * @param dayOfMonth Day number of the month. The first day of the month has value 1
 * @param month Month number. The first month has value 1
 * @param year Year
 * @param hourOfDay Hour of the day. Used for the 24-hour clock. E.g., at 10:04:15.250 PM the hourOfDay is 22
 * @param minutes Minutes within the hour. E.g., at 10:04:15.250 PM the minutes is 4
 * @param seconds Second within the minute. E.g., at 10:04:15.250 PM the seconds is 15
 */
private fun Calendar.specify(
    dayOfMonth: Int? = null,
    month: Int? = null,
    year: Int? = null,
    hourOfDay: Int? = null,
    minutes: Int? = null,
    seconds: Int? = null
): Calendar = apply {
    dayOfMonth?.let { set(Calendar.DATE, it) }
    month?.let{ set(Calendar.MONTH, it - 1) }
    year?.let { set(Calendar.YEAR, it) }
    hourOfDay?.let { set(Calendar.HOUR_OF_DAY, it) }
    minutes?.let { set(Calendar.MINUTE, it) }
    seconds?.let { set(Calendar.SECOND, it) }
}

fun Calendar.plusDays(amountOfDays: Int): Calendar {
    return (clone() as Calendar).apply { add(Calendar.DATE, amountOfDays) }
}

fun MemorableDate.isToday(): Boolean {
    val nowDayNumber = getNowDayNumber()
    val nowMonthNumber = getNowMonthNumber()
    return dayNumber == nowDayNumber && monthNumber == nowMonthNumber
}

fun getNowDayNumber(): Int {
    return Calendar.getInstance().get(Calendar.DATE)
}

fun getNowMonthNumber(): Int {
    return Calendar.getInstance().get(Calendar.MONTH) + 1
}