package com.example.lifediary.presentation.ui.calendar

import android.view.View
import android.widget.TextView
import com.example.lifediary.presentation.utils.dates.isToday
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek

abstract class BaseCalendarDayViewContainer(view: View) : ViewContainer(view) {
    private lateinit var day: CalendarDay
    abstract val dayTextView: TextView
    abstract val selectedBackgroundView: View
    abstract val noteIconView: View
    abstract val eventIconView: View
    abstract val menstruationIconView: View
    abstract val estimatedNextMenstruationIconView: View

    abstract fun getOutputFormattedNumberFor(day: CalendarDay): String
    abstract fun getCalendarDayTextColor(day: CalendarDay): Int

    fun setup(
        day: CalendarDay,
        onDayClick: (CalendarDay) -> Unit
    ) {
        this.day = day
        setDayNumber(day)
        setDayNumberColor(day)
        setOnDayClickListener(onDayClick)
    }

    private fun setDayNumber(day: CalendarDay) {
        dayTextView.text = getOutputFormattedNumberFor(day)
    }

    private fun setDayNumberColor(day: CalendarDay) {
        dayTextView.setTextColor(getCalendarDayTextColor(day))
    }

    private fun setOnDayClickListener(onClick: (CalendarDay) -> Unit) {
        view.setOnClickListener { onClick(day) }
    }

    fun drawDesignations(
        day: CalendarDay,
        isNoteOrToDoListExists: Boolean,
        isMemorableDatesExist: Boolean,
        isDayOfMenstruationPeriod: Boolean,
        isDayOfNextMenstruationPeriod: Boolean
    ) {
        selectedBackgroundView.setVisibility(day.isToday())
        noteIconView.setVisibility(isNoteOrToDoListExists)
        eventIconView.setVisibility(isMemorableDatesExist)
        menstruationIconView.setVisibility(isDayOfMenstruationPeriod)
        estimatedNextMenstruationIconView.setVisibility(isDayOfNextMenstruationPeriod)
    }

    private fun View.setVisibility(isVisible: Boolean) {
        visibility = if(isVisible) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}

abstract class BaseCalendarMonthViewContainer(view: View) : ViewContainer(view) {
    abstract fun setupTitleView(month: CalendarMonth)
    abstract fun setupDaysOfWeekView(daysOfWeek: Array<DayOfWeek>)
}