package com.example.lifediary.domain.utils

import com.example.lifediary.domain.models.MemorableDate

fun MemorableDate.howManyYearsAgo(): Int? {
	val year = year ?: return null
	val thisYear = CalendarBuilder().build().getYear()
	val diffYears = thisYear - year
	if(diffYears <= 0) return null
	return thisYear - year
}

fun MemorableDate.isToday(): Boolean {
	val nowDayNumber = getNowDayNumber()
	val nowMonthNumber = getNowMonthNumber()
	return dayNumber == nowDayNumber && monthNumber == nowMonthNumber
}

fun List<MemorableDate>.sortBasedToday(): List<MemorableDate> {
	if(isEmpty()) return this

	val nearestDateInThisMonth = find {
		it.monthNumber == getNowMonthNumber() && it.dayNumber >= getNowDayNumber()
	}
	nearestDateInThisMonth?.let { return splitAndSwap(indexOf(it)) }

	val nearestDate = find { it.monthNumber > getNowMonthNumber() }
	nearestDate?.let { return splitAndSwap(indexOf(it)) }

	return this
}

private fun <T> List<T>.splitAndSwap(splitIndex: Int): List<T> {
	val head = subList(0, splitIndex)
	val tail = subList(splitIndex, lastIndex + 1)
	return tail.plus(head)
}