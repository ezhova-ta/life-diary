package com.example.lifediary.ui.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.data.domain.DateNote
import com.example.lifediary.data.repositories.DateNoteRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CalendarViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var noteRepository: DateNoteRepository

	var noteList: LiveData<List<DateNote>>

	init {
		bindAppScope()
		noteList = noteRepository.getAllNote()
	}

	fun onDateClick(day: Day) {
		router.navigateTo(Screens.getCalendarDateScreen(day))
	}
}