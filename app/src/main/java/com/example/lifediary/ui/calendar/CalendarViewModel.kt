package com.example.lifediary.ui.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.data.repositories.NotesRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CalendarViewModel : BaseViewModel() {
	@Inject
	lateinit var router: Router
	@Inject
	lateinit var notesRepository: NotesRepository

	var notesList: LiveData<List<Notes>>

	init {
		bindAppScope()
		notesList = notesRepository.getAllNotes()
	}

	fun onDateClick(day: Day) {
		router.navigateTo(Screens.getCalendarDateScreen(day))
	}
}