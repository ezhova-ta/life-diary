package com.example.lifediary.ui.calendar

import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.toDateString
import com.github.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

class CalendarDateViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router

	lateinit var date: Calendar
	val title: String by lazy { date.toDateString() }

	init {
		bindAppScope()

	}
}