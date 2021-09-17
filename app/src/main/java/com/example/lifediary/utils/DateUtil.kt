package com.example.lifediary.utils

import com.kizitonwose.calendarview.model.CalendarDay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun Calendar.toLong(): Long {
    return timeInMillis
}

fun Long.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar
}

fun Day.toDateString(): String {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(this.toCalendar().time)
}

fun Day.toDateTimeString(withMilliseconds: Boolean = false): String {
    val pattern = if(withMilliseconds) "dd.MM.yyyy HH:mm:ss" else "dd.MM.yyyy HH:mm"
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this.toCalendar().time)
}

private fun Day.toCalendar(): Calendar {
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

fun CalendarDay.isToday(): Boolean {
    val today = LocalDate.now(ZoneId.systemDefault())
    return date.isEqual(today)
}