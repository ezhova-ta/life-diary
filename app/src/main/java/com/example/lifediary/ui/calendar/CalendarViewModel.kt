package com.example.lifediary.ui.calendar

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.example.lifediary.data.repositories.DateNoteRepository
import com.example.lifediary.data.repositories.ToDoListRepository
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CalendarViewModel : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var noteRepository: DateNoteRepository
	@Inject lateinit var doToDoListRepository: ToDoListRepository
	val daysWithNotesOrToDoList = MediatorLiveData<List<Day>>()

	init {
		bindAppScope()

		val daysWithNote = noteRepository.getAllNotes().map { it.map { it.day } }
		val daysWithToDoList = doToDoListRepository.getAllToDoLists().map { it.map { it.day } }
		daysWithNotesOrToDoList.value = listOf()

		daysWithNotesOrToDoList.addSource(daysWithNote) {
			val daysWithToDoListValue = daysWithToDoList.value ?: listOf()
			val allDays = it.plus(daysWithToDoListValue)
			daysWithNotesOrToDoList.value = allDays
		}

		daysWithNotesOrToDoList.addSource(daysWithToDoList) {
			val daysWithNoteValue = daysWithNote.value ?: listOf()
			val allDays = it.plus(daysWithNoteValue)
			daysWithNotesOrToDoList.value = allDays
		}
	}

	fun onDateClick(day: Day) {
		router.navigateTo(Screens.getCalendarDateScreen(day))
	}
}