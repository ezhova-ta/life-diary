package com.example.lifediary.ui.calendar

import com.example.lifediary.ui.BaseViewModel
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CalendarDateViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router

	init {
		bindAppScope()
	}
}