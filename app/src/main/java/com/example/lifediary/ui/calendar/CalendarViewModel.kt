package com.example.lifediary.ui.calendar

import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.toLong
import com.github.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

class CalendarViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router

	init {
		bindAppScope()
	}

	fun onDateClick(day: Day) {
		router.navigateTo(Screens.getCalendarDateScreen(day))
	}
}