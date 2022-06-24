package com.example.lifediary.presentation.ui.calendar

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.isVisible
import com.example.lifediary.R
import com.example.lifediary.databinding.CalendarDayLayoutBinding
import com.example.lifediary.databinding.CalendarMonthLayoutBinding
import com.example.lifediary.presentation.utils.startWithCapitalLetter
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class StandardCalendarDayViewContainer(view: View) : BaseCalendarDayViewContainer(view) {
	private val binding = CalendarDayLayoutBinding.bind(view)
	private val context: Context get() = view.context
	override val dayTextView get() = binding.dayText
	override val selectedBackgroundView get() = binding.selectedBackground
	override val noteIconView get() = binding.noteIcon
	override val eventIconView get() = binding.eventIcon
	override val menstruationIconView get() = binding.menstruationIcon
	override val estimatedNextMenstruationIconView get() = binding.estimatedNextMenstruationIcon

	override fun getOutputFormattedNumberFor(day: CalendarDay): String {
		return day.date.dayOfMonth.toString()
	}

	override fun getCalendarDayTextColor(day: CalendarDay): Int {
		return context.resources.getColor(
			if(day.owner == DayOwner.THIS_MONTH) R.color.app_medium_dark_gray else R.color.app_medium_gray,
			context.theme
		)
	}
}

class StandardCalendarMonthViewContainer(view: View) : BaseCalendarMonthViewContainer(view) {
	private val binding = CalendarMonthLayoutBinding.bind(view)

	override fun setupTitleView(month: CalendarMonth) {
		val monthString = month
			.yearMonth
			.month
			.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
			.startWithCapitalLetter()

		val year = month.year
		binding.monthTextView.text = String.format("%s %d", monthString, year)
	}

	override fun setupDaysOfWeekView(daysOfWeek: Array<DayOfWeek>) {
		try {
			for(i in 0..daysOfWeek.lastIndex) {
				val dayView = binding.daysOfWeekContainer[i] as TextView
				val dayName = daysOfWeek[i].getDisplayName(TextStyle.SHORT, Locale.getDefault())
				dayView.text = dayName
			}
			binding.daysOfWeekContainer.isVisible = true
		} catch(e: IndexOutOfBoundsException) {
			binding.daysOfWeekContainer.isVisible = false
		}
	}
}