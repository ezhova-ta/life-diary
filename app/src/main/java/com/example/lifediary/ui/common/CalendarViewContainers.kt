package com.example.lifediary.ui.common

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.isVisible
import com.example.lifediary.R
import com.example.lifediary.databinding.CalendarDayLayoutBinding
import com.example.lifediary.databinding.CalendarMonthLayoutBinding
import com.example.lifediary.utils.isToday
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class CalendarDayViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)
    private lateinit var day: CalendarDay
    private val context: Context get() = view.context

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
        binding.dayText.text = day.getDayNumber()
    }

    private fun CalendarDay.getDayNumber(): String {
        return date.dayOfMonth.toString()
    }

    private fun setDayNumberColor(day: CalendarDay) {
        binding.dayText.setTextColor(day.getTextColor())
    }

    private fun CalendarDay.getTextColor(): Int = context.resources.getColor(
        if(owner == DayOwner.THIS_MONTH) R.color.app_medium_dark_gray else R.color.app_medium_gray,
        context.theme
    )

    private fun setOnDayClickListener(onClick: (CalendarDay) -> Unit) {
        view.setOnClickListener { onClick(day) }
    }

    fun drawDesignations(
        day: CalendarDay,
        isNoteOrToDoListExists: Boolean,
        isMemorableDatesExist: Boolean
    ) {
        with(binding) {
            selectedBackground.setVisibility(day.isToday())
            noteIcon.setVisibility(isNoteOrToDoListExists)
            eventIcon.setVisibility(isMemorableDatesExist)
        }
    }

    private fun View.setVisibility(isVisible: Boolean) {
        visibility = if(isVisible) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}

class CalendarMonthViewContainer(view: View) : ViewContainer(view) {
    private val binding = CalendarMonthLayoutBinding.bind(view)
    private val monthTextView = binding.monthTextView

    fun setupTitle(month: CalendarMonth) {
        monthTextView.text = String.format("%s %d", month.yearMonth.month.name, month.year)
    }

    fun setupDaysOfWeek(daysOfWeek: Array<DayOfWeek>) {
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