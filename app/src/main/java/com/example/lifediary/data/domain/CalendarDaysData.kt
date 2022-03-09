package com.example.lifediary.data.domain

data class CalendarDaysData(
	val daysWithNotesOrToDoList: List<Day>,
	val memorableDates: List<MemorableDate>,
	val menstruationPeriods: List<MenstruationPeriod>,
	val estimatedNextMenstruationPeriod: MenstruationPeriod?
)
