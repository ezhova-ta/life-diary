package com.example.lifediary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.data.domain.MenstruationPeriod
import com.example.lifediary.data.repositories.*
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.Day
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class CalendarViewModel : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var noteRepository: DateNoteRepository
	@Inject lateinit var doToDoListRepository: ToDoListRepository
	@Inject lateinit var memorableDatesRepository: MemorableDatesRepository
	@Inject lateinit var womanSectionRepository: WomanSectionRepository
	@Inject lateinit var settingsRepository: SettingsRepository
	val daysWithNotesOrToDoList = MediatorLiveData<List<Day>>()
	val memorableDates: LiveData<List<MemorableDate>> by lazy { memorableDatesRepository.getDates() }
	val menstruationPeriodList: LiveData<List<MenstruationPeriod>> by lazy {
		womanSectionRepository.getAllMenstruationPeriods()
	}
	val isSectionForWomanVisible: LiveData<Boolean> by lazy { settingsRepository.getWomanSectionEnabled() }

	init {
		bindScope()
		setupDaysWithNotesOrToDoList()
	}

	override fun bindScope() {
		val calendarScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.CALENDAR_SCOPE)
		Toothpick.inject(this, calendarScope)
	}

	private fun setupDaysWithNotesOrToDoList() {
		val daysWithNote = getDaysWithNote()
		val daysWithToDoList = getDaysWithToDoList()
		daysWithNotesOrToDoList.value = listOf()

		daysWithNotesOrToDoList.addSource(daysWithNote) {
			val daysWithToDoListValue = daysWithToDoList.value ?: listOf()
			val allDays = it.plus(daysWithToDoListValue).distinct()
			daysWithNotesOrToDoList.value = allDays
		}

		daysWithNotesOrToDoList.addSource(daysWithToDoList) {
			val daysWithNoteValue = daysWithNote.value ?: listOf()
			val allDays = it.plus(daysWithNoteValue).distinct()
			daysWithNotesOrToDoList.value = allDays
		}
	}

	private fun getDaysWithNote(): LiveData<List<Day>> {
		return noteRepository.getAllNotes().map { noteList ->
			noteList.map { note ->
				note.day
			}
		}
	}

	private fun getDaysWithToDoList():  LiveData<List<Day>> {
		return doToDoListRepository.getAllToDoLists().map { toDoList ->
			toDoList.map { toDoListItem ->
				toDoListItem.day
			}
		}
	}

	fun onDateClick(day: Day) {
		router.navigateTo(Screens.getCalendarDateScreen(day))
	}
}