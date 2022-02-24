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
    val format = if(withYear) {
        SimpleDateFormat(DATE_FORMAT_WITH_YEAR, Locale.getDefault())
    } else {
        SimpleDateFormat(DATE_FORMAT_WITHOUT_YEAR, Locale.getDefault())
    }
    return format.format(this.toCalendar().time)
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
    return Calendar.getInstance().apply {
        set(Calendar.DATE, dayNumber)
        set(Calendar.MONTH, monthNumber - 1)
        set(Calendar.YEAR, year)
    }
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

fun CalendarDay.isSameDay(memorableDate: MemorableDate): Boolean {
    return date.dayOfMonth == memorableDate.dayNumber && date.monthValue == memorableDate.monthNumber
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