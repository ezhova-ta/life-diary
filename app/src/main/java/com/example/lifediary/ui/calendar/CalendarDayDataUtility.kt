package com.example.lifediary.ui.calendar

import com.example.lifediary.data.domain.CalendarDaysData
import com.example.lifediary.utils.dates.isSameDay
import com.example.lifediary.utils.dates.isSameDayInYear
import com.example.lifediary.utils.dates.isWithinInterval
import com.kizitonwose.calendarview.model.CalendarDay

class CalendarDayDataUtility(
	private val day: CalendarDay,
	private val data: CalendarDaysData?
) {
	fun containsNoteOrToDoList(): Boolean {
		return data?.daysWithNotesOrToDoList?.find { it.isSameDay(day) } != null
	}

	fun containsMemorableDates(): Boolean {
		return data?.memorableDates?.find { day.isSameDayInYear(it) } != null
	}

	fun isDayOfMenstruationPeriod(): Boolean {
		return data?.menstruationPeriods?.find {
			day.isWithinInterval(it.startDate, it.endDate)
		} != null
	}

	fun isDayOfNextMenstruationPeriod(): Boolean {
		val menstruationPeriod = data?.estimatedNextMenstruationPeriod ?: return false
		return day.isWithinInterval(menstruationPeriod.startDate, menstruationPeriod.endDate)
	}
}