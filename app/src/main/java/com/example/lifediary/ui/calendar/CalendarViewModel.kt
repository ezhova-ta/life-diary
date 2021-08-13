package com.example.lifediary.ui.calendar

import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

class CalendarViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router

	init {
		bindAppScope()
	}

	fun onDateClick(date: Calendar) {
		router.navigateTo(Screens.getCalendarDateScreen(date.timeInMillis))
	}
}