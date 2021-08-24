package com.example.lifediary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.data.domain.Notes
import com.example.lifediary.databinding.FragmentCalendarBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.ui.common.CalendarDayViewContainer
import com.example.lifediary.ui.common.CalendarMonthViewContainer
import com.example.lifediary.utils.isSameDay
import com.example.lifediary.utils.isToday
import com.example.lifediary.utils.toDomainDay
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class CalendarFragment : BaseFragment() {
    override val viewModel: CalendarViewModel  by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var notesList = listOf<Notes>()

    companion object {
        fun getInstance(): Fragment {
            return CalendarFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupCalendarView()
        return binding.root
    }

    private fun setupCalendarView() {
        binding.calendarView.dayBinder = createCalendarDayBinder()
        binding.calendarView.monthHeaderBinder = createCalendarMonthBinder()
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth) // TODO Execute only once
        setupDisplayingNotesIconsInCalendar()
    }

    private fun setupDisplayingNotesIconsInCalendar() {
        viewModel.notesList.observe(viewLifecycleOwner) { notesList ->
            this.notesList = notesList
            binding.calendarView.notifyCalendarChanged()
        }
    }

    private fun createCalendarDayBinder() = object : DayBinder<CalendarDayViewContainer> {
        override fun create(view: View) = CalendarDayViewContainer(view)

        override fun bind(container: CalendarDayViewContainer, day: CalendarDay) {
            container.day = day
            container.dayTextView.text = day.getDayNumber()
            container.dayTextView.setTextColor(day.getTextColor())
            container.setOnClickListener { viewModel.onDateClick(it.toDomainDay()) }

            if(day.isToday()) {
                container.setSelectedStyle()
            } else {
                container.setNormalStyle()
            }

            if(isNotesExistFor(day)) {
                container.showNoteIcon()
            } else {
                container.hideNoteIcon()
            }
        }

        private fun isNotesExistFor(day: CalendarDay): Boolean {
            return notesList.find { it.day.isSameDay(day) } != null
        }
    }

    private fun createCalendarMonthBinder() = object : MonthHeaderFooterBinder<CalendarMonthViewContainer> {
        override fun create(view: View) = CalendarMonthViewContainer(view)

        override fun bind(container: CalendarMonthViewContainer, month: CalendarMonth) {
            container.monthTextView.text = String.format("%s %d", month.yearMonth.month.name, month.year)
        }
    }

    private fun CalendarDay.getDayNumber(): String {
        return date.dayOfMonth.toString()
    }

    private fun CalendarDay.getTextColor(): Int {
        return if(owner == DayOwner.THIS_MONTH) {
            resources.getColor(R.color.app_dark_gray, requireContext().theme)
        } else {
            resources.getColor(R.color.app_medium_gray, requireContext().theme)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}