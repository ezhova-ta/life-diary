package com.example.lifediary.domain.utils

import java.time.temporal.ChronoUnit
import java.util.*

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