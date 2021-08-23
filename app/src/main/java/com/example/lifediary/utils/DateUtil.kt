package com.example.lifediary.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun Calendar.toLong(): Long {
    return timeInMillis
}

fun Long.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar
}

fun Calendar.toDateString(): String {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(time)
}

fun Calendar.toDateTimeString(withMilliseconds: Boolean = false): String {
    val pattern = if(withMilliseconds) "dd.MM.yyyy HH:mm:ss" else "dd.MM.yyyy HH:mm"
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(time)
}

fun Calendar.isSameDay(dateInSeconds: Long): Boolean {
    val dateInMillis = dateInSeconds * 1000
    val date = Calendar.getInstance()
    date.timeInMillis = dateInMillis

    return get(Calendar.DATE) == date.get(Calendar.DATE) &&
        get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
        get(Calendar.YEAR) == date.get(Calendar.YEAR)
}

fun LocalDate.toCalendar(): Calendar = Calendar.getInstance().apply {
    set(Calendar.DATE, dayOfMonth)
    set(Calendar.MONTH, monthValue - 1)
    set(Calendar.YEAR, year)
}

fun Calendar.toLocalDate(): LocalDate = LocalDate.of(
    get(Calendar.DATE),
    get(Calendar.MONTH + 1),
    get(Calendar.YEAR)
)