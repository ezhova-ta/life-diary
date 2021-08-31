package com.example.lifediary.ui.common

import android.view.View
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.isVisible
import com.example.lifediary.databinding.CalendarDayLayoutBinding
import com.example.lifediary.databinding.CalendarMonthLayoutBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

class CalendarDayViewContainer(view: View) : ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)
    val dayTextView = binding.dayText
    lateinit var day: CalendarDay

    fun setOnClickListener(onClick: (CalendarDay) -> Unit) {
        view.setOnClickListener {
            onClick(day)
        }
    }

    fun setSelectedStyle() {
        binding.selectedBackground.visibility = View.VISIBLE
    }

    fun setNormalStyle() {
        binding.selectedBackground.visibility = View.INVISIBLE
    }

    fun showNoteIcon() {
        binding.noteIcon.visibility = View.VISIBLE
    }

    fun hideNoteIcon() {
        binding.noteIcon.visibility = View.INVISIBLE
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