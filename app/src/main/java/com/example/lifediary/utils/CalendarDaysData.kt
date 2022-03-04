package com.example.lifediary.utils

import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.domain.MenstruationPeriod

// TODO Is this the right class name?
data class CalendarDaysData(
	val daysWithNotesOrToDoList: List<Day>,
	val memorableDates: List<MemorableDate>,
	val menstruationPeriods: List<MenstruationPeriod>,
	val estimatedNextMenstruationPeriod: MenstruationPeriod?
)
