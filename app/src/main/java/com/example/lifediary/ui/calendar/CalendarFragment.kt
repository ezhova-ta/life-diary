package com.example.lifediary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.databinding.FragmentCalendarBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.ui.common.CalendarDayViewContainer
import com.example.lifediary.ui.common.CalendarMonthViewContainer
import com.example.lifediary.utils.Day
import com.example.lifediary.utils.isSameDay
import com.example.lifediary.utils.toDomain
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class CalendarFragment : BaseFragment() {
    override val viewModel: CalendarViewModel  by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var daysWithNotesOrToDoList = listOf<Day>()
    private var memorableDates = listOf<MemorableDate>()

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
        binding.calendarView.monthHeaderBinder = createCalendarMonthBinder(getDaysOfWeek())
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = getWeekFields().firstDayOfWeek
        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth) // TODO Execute only once
        setupDisplayingNoteIconsInCalendar()
        setupDisplayingEventIconsInCalendar()
    }

    private fun getDaysOfWeek(): Array<DayOfWeek> {
        val firstDayOfWeek = getWeekFields().firstDayOfWeek
        val daysOfWeek = DayOfWeek.values()

        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val rightPart = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
            val leftPart = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
            return rightPart + leftPart
        }
        return daysOfWeek
    }

    private fun getWeekFields(): WeekFields {
        return WeekFields.of(Locale.getDefault())
    }

    private fun setupDisplayingNoteIconsInCalendar() {
        viewModel.daysWithNotesOrToDoList.observe(viewLifecycleOwner) { days ->
            daysWithNotesOrToDoList = days
            binding.calendarView.notifyCalendarChanged()
        }
    }

    private fun setupDisplayingEventIconsInCalendar() {
        viewModel.memorableDates.observe(viewLifecycleOwner) { dates ->
            memorableDates = dates
            binding.calendarView.notifyCalendarChanged()
        }
    }

    private fun createCalendarDayBinder() = object : DayBinder<CalendarDayViewContainer> {
        override fun create(view: View) = CalendarDayViewContainer(view)

        override fun bind(container: CalendarDayViewContainer, day: CalendarDay) {
            container.setup(day) { viewModel.onDateClick(it.toDomain()) }
            lifecycleScope.launch { setStyle(container, day) }
        }

        private suspend fun setStyle(container: CalendarDayViewContainer, day: CalendarDay) {
            container.setStyle(day, isNoteOrToDoListExistsFor(day), isMemorableDatesExistFor(day))
        }

        private suspend fun isNoteOrToDoListExistsFor(day: CalendarDay): Boolean {
            return daysWithNotesOrToDoList.find { it.isSameDay(day) } != null
        }

        private suspend fun isMemorableDatesExistFor(day: CalendarDay): Boolean {
            return memorableDates.find { day.isSameDay(it) } != null
        }
    }

    private fun createCalendarMonthBinder(daysOfWeek: Array<DayOfWeek>) =
        object : MonthHeaderFooterBinder<CalendarMonthViewContainer> {
            override fun create(view: View) = CalendarMonthViewContainer(view)

            override fun bind(container: CalendarMonthViewContainer, month: CalendarMonth) {
                container.setupTitle(month)
                container.setupDaysOfWeek(daysOfWeek)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}