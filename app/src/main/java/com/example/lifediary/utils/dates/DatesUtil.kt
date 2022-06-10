package com.example.lifediary.utils.dates

import com.example.lifediary.data.domain.Day
import java.time.temporal.ChronoUnit
import java.util.*

fun getDateString(dayNumber: Int, monthNumber: Int, year: Int? = null): String {
    return if(year == null) {
        val thisYear = CalendarBuilder().build().getYear()
        val day = Day(dayNumber, monthNumber, thisYear)
        day.toDateString(false)
    } else {
        val day = Day(dayNumber, monthNumber, year)
        day.toDateString()
    }
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

fun getNowDayNumber(): Int {
    return CalendarBuilder().build().getDayOfMonthNumber()
}

fun getNowMonthNumber(): Int {
    return CalendarBuilder().build().getMonthNumber()
}