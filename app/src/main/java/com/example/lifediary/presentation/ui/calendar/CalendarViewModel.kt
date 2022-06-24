package com.example.lifediary.presentation.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.presentation.CalendarDaysData
import com.example.lifediary.domain.models.Day
import com.example.lifediary.di.DiScopes
import com.example.lifediary.domain.usecases.calendar.GetAllToDoListsUseCase
import com.example.lifediary.domain.usecases.calendar.GetDateNotesUseCase
import com.example.lifediary.domain.usecases.memorable_dates.GetMemorableDatesUseCase
import com.example.lifediary.domain.usecases.settings.GetWomanSectionEnabledUseCase
import com.example.lifediary.domain.usecases.woman_section.GetAllMenstruationPeriodsUseCase
import com.example.lifediary.domain.usecases.woman_section.GetEstimatedNextMenstruationPeriodUseCase
import com.example.lifediary.presentation.navigation.Screens
import com.example.lifediary.presentation.ui.BaseViewModel
import com.example.lifediary.presentation.utils.livedata.FourSourceLiveData
import com.example.lifediary.presentation.utils.livedata.TwoSourceLiveData
import com.github.terrakok.cicerone.Router
import toothpick.Toothpick
import javax.inject.Inject

class CalendarViewModel : BaseViewModel() {
	@Inject lateinit var router: Router
	@Inject lateinit var getDateNotesUseCase: GetDateNotesUseCase
	@Inject lateinit var getAllToDoListsUseCase: GetAllToDoListsUseCase
	@Inject lateinit var getMemorableDatesUseCase: GetMemorableDatesUseCase
	@Inject lateinit var getAllMenstruationPeriodsUseCase: GetAllMenstruationPeriodsUseCase
	@Inject lateinit var getEstimatedNextMenstruationPeriodUseCase: GetEstimatedNextMenstruationPeriodUseCase
	@Inject lateinit var getWomanSectionEnabledUseCase: GetWomanSectionEnabledUseCase

	val calendarDaysData by lazy { createCalendarDaysData() }
	val isSectionForWomanVisible by lazy { getWomanSectionEnabledUseCase() }

	init {
		bindScope()
	}

	override fun bindScope() {
		val calendarScope = Toothpick.openScopes(DiScopes.APP_SCOPE, DiScopes.CALENDAR_SCOPE)
		Toothpick.inject(this, calendarScope)
	}

	// TODO Move to UseCase ?
	private fun createCalendarDaysData(): LiveData<CalendarDaysData?> {
		return FourSourceLiveData(
			getDaysWithNotesOrToDoList(),
			getMemorableDatesUseCase(),
			getAllMenstruationPeriodsUseCase(),
			getEstimatedNextMenstruationPeriodUseCase()
		) { daysWithNotesOrToDoList, memorableDates, menstruationPeriodList, nextMenstruationPeriod ->
			daysWithNotesOrToDoList ?: return@FourSourceLiveData null
			memorableDates ?: return@FourSourceLiveData null
			menstruationPeriodList ?: return@FourSourceLiveData null

			CalendarDaysData(
				daysWithNotesOrToDoList,
				memorableDates,
				if(isSectionForWomanVisible()) menstruationPeriodList else listOf(),
				if(isSectionForWomanVisible()) nextMenstruationPeriod else null
			)
		}
	}

	private fun isSectionForWomanVisible(): Boolean {
		return isSectionForWomanVisible.value == true
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
		return getDateNotesUseCase().map { noteList ->
			noteList.map { note ->
				note.day
			}
		}
	}

	private fun getDaysWithToDoList():  LiveData<List<Day>> {
		return getAllToDoListsUseCase().map { toDoList ->
			toDoList.map { toDoListItem ->
				toDoListItem.day
			}
		}
	}

	fun onDateClick(day: Day) {
		router.navigateTo(Screens.getCalendarDateScreen(day))
	}

	override fun onCleared() {
		Toothpick.closeScope(DiScopes.CALENDAR_SCOPE)
		super.onCleared()
	}
}