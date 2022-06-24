package com.example.lifediary.presentation

import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.MemorableDate
import com.example.lifediary.domain.models.MenstruationPeriod

data class CalendarDaysData(
	val daysWithNotesOrToDoList: List<Day>,
	val memorableDates: List<MemorableDate>,
	val menstruationPeriods: List<MenstruationPeriod>,
	val estimatedNextMenstruationPeriod: MenstruationPeriod?
)
