package com.example.lifediary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.repositories.*
import com.example.lifediary.di.DiScopes
import com.example.lifediary.navigation.Screens
import com.example.lifediary.ui.BaseViewModel
import com.example.lifediary.utils.CalendarDaysData
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.FourSourceLiveData
import com.example.lifediary.utils.TwoSourceLiveData
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
	val calendarDaysData by lazy { createCalendarDaysData() }
	val isSectionForWomanVisible by lazy { settingsRepository.getWomanSectionEnabled() }

	init {
		bindScope()
	}

	override fun bindScope() {
		val calendarScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.CALENDAR_SCOPE)
		Toothpick.inject(this, calendarScope)
	}

	private fun createCalendarDaysData(): LiveData<CalendarDaysData?> {
		return FourSourceLiveData(
			getDaysWithNotesOrToDoList(),
			memorableDatesRepository.getDates(),
			womanSectionRepository.getAllMenstruationPeriods(),
			womanSectionRepository.getEstimatedNextMenstruationPeriod()
		) { daysWithNotesOrToDoList, memorableDates, menstruationPeriodList, nextMenstruationPeriod ->
			daysWithNotesOrToDoList ?: return@FourSourceLiveData null
			memorableDates ?: return@FourSourceLiveData null
			menstruationPeriodList ?: return@FourSourceLiveData null

			CalendarDaysData(
				daysWithNotesOrToDoList,
				memorableDates,
				if(isSectionForWomanVisible.value == true) menstruationPeriodList else listOf(),
				if(isSectionForWomanVisible.value == true) nextMenstruationPeriod else null
			)
		}
	}

	private fun getDaysWithNotesOrToDoList(): LiveData<List<Day>> {
		return TwoSourceLiveData(
			getDaysWithNote(),
			getDaysWithToDoList()
		) { daysWithNote, daysWithToDoList ->
			daysWithNote ?: return@TwoSourceLiveData listOf()
			daysWithToDoList ?: return@TwoSourceLiveData listOf()
			daysWithNote.plus(daysWithToDoList).distinct()
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