package com.example.lifediary.domain.models

import com.example.lifediary.presentation.utils.dates.CalendarBuilder
import com.example.lifediary.presentation.utils.dates.getNowDayNumber
import com.example.lifediary.presentation.utils.dates.getNowMonthNumber
import com.example.lifediary.presentation.utils.dates.getYear

data class MemorableDate(
	val id: Long? = null,
	var name: String,
	var dayNumber: Int,
	var monthNumber: Int,
	var year: Int? = null
) {
	fun howManyYearsAgo(): Int? {
		val year = year ?: return null
		val thisYear = CalendarBuilder().build().getYear()
		val diffYears = thisYear - year
		if(diffYears <= 0) return null
		return thisYear - year
	}

	fun isToday(): Boolean {
		val nowDayNumber = getNowDayNumber()
		val nowMonthNumber = getNowMonthNumber()
		return dayNumber == nowDayNumber && monthNumber == nowMonthNumber
	}
}
