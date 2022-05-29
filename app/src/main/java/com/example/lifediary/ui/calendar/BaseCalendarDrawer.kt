package com.example.lifediary.ui.calendar

import android.view.View
import com.example.lifediary.data.domain.CalendarDaysData
import com.example.lifediary.utils.*
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.MonthScrollListener
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

abstract class BaseCalendarDrawer(private val calendarView: CalendarView) {
	var onScrollMonthListener: ((YearMonth) -> Unit)? = null
	private var calendarDaysData: CalendarDaysData? = null

	protected abstract fun getCalendarDayViewContainer(view: View): BaseCalendarDayViewContainer
	protected abstract fun getCalendarMonthViewContainer(view: View): BaseCalendarMonthViewContainer
	protected abstract fun getMonthNumberInPastToDisplayInCalendar(): Long
	protected abstract fun getMonthNumberInFutureToDisplayInCalendar(): Long

	fun onDataChanged(calendarDaysData: CalendarDaysData) {
		this.calendarDaysData = calendarDaysData
		calendarView.notifyCalendarChanged()
	}

	fun scrollToMonth(month: YearMonth) {
		calendarView.scrollToMonth(month)
	}

	fun initialize(onDayClick: (CalendarDay) -> Unit = {}) {
		calendarView.dayBinder = createCalendarDayBinder(onDayClick)
		calendarView.monthHeaderBinder = createCalendarMonthBinder(getDaysOfWeek())
		val currentMonth = YearMonth.now()
		val firstMonth = currentMonth.minusMonths(getMonthNumberInPastToDisplayInCalendar())
		val lastMonth = currentMonth.plusMonths(getMonthNumberInFutureToDisplayInCalendar())
		val firstDayOfWeek = getWeekFields().firstDayOfWeek
		calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
		calendarView.monthScrollListener = object : MonthScrollListener {
			override fun invoke(month: CalendarMonth) { onScrollMonthListener?.invoke(month.yearMonth) }
		}
	}

	private fun createCalendarDayBinder(onDayClick: (CalendarDay) -> Unit) =
		object : DayBinder<BaseCalendarDayViewContainer> {
			override fun create(view: View) = getCalendarDayViewContainer(view)

			override fun bind(container: BaseCalendarDayViewContainer, day: CalendarDay) {
				container.setup(day) { onDayClick(day) }
				// TODO Refactoring. How? :(
				container.drawDesignations(
					day,
					CalendarDayDataHolder(day, calendarDaysData).containsNoteOrToDoList(),
					CalendarDayDataHolder(day, calendarDaysData).containsMemorableDates(),
					CalendarDayDataHolder(day, calendarDaysData).isDayOfMenstruationPeriod(),
					CalendarDayDataHolder(day, calendarDaysData).isDayOfNextMenstruationPeriod()
				)
			}
		}

	private fun createCalendarMonthBinder(daysOfWeek: Array<DayOfWeek>) =
		object : MonthHeaderFooterBinder<BaseCalendarMonthViewContainer> {
			override fun create(view: View) = getCalendarMonthViewContainer(view)

			override fun bind(container: BaseCalendarMonthViewContainer, month: CalendarMonth) {
				container.setupTitleView(month)
				container.setupDaysOfWeekView(daysOfWeek)
			}
		}

	private fun getDaysOfWeek(): Array<DayOfWeek> {
		val firstDayOfWeek = getWeekFields().firstDayOfWeek
		return DayOfWeek.values().startFrom(firstDayOfWeek)
	}

	private fun getWeekFields(): WeekFields {
		return WeekFields.of(Locale.getDefault())
	}
}