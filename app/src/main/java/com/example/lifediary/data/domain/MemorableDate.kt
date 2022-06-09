package com.example.lifediary.data.domain

import java.util.*

data class MemorableDate(
	val id: Long? = null,
	var name: String,
	var dayNumber: Int,
	var monthNumber: Int,
	var year: Int? = null
) {
	fun howManyYearsAgo(): Int? {
		val year = year ?: return null
		val thisYear = Calendar.getInstance().get(Calendar.YEAR)
		val diffYears = thisYear - year
		if(diffYears <= 0) return null
		return thisYear - year
	}
}
