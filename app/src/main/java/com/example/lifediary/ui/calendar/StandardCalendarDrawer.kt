package com.example.lifediary.ui.calendar

import android.view.View
import com.example.lifediary.utils.UiConstants.Calendar.DEFAULT_MONTH_NUMBER_IN_FUTURE_TO_DISPLAY_IN_CALENDAR
import com.example.lifediary.utils.UiConstants.Calendar.DEFAULT_MONTH_NUMBER_IN_PAST_TO_DISPLAY_IN_CALENDAR
import com.kizitonwose.calendarview.CalendarView

class StandardCalendarDrawer(calendarView: CalendarView) : BaseCalendarDrawer(calendarView) {
	override fun getCalendarDayViewContainer(view: View): BaseCalendarDayViewContainer {
		return StandardCalendarDayViewContainer(view)
	}

	override fun getCalendarMonthViewContainer(view: View): BaseCalendarMonthViewContainer {
		return StandardCalendarMonthViewContainer(view)
	}

	override fun getMonthNumberInPastToDisplayInCalendar(): Long {
		return DEFAULT_MONTH_NUMBER_IN_PAST_TO_DISPLAY_IN_CALENDAR
	}

	override fun getMonthNumberInFutureToDisplayInCalendar(): Long {
		return DEFAULT_MONTH_NUMBER_IN_FUTURE_TO_DISPLAY_IN_CALENDAR
	}
}