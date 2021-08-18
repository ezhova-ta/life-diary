package com.example.lifediary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentCalendarBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.ui.common.CalendarDayViewContainer
import com.example.lifediary.utils.toCalendar
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.*

class CalendarFragment : BaseFragment() {
    override val viewModel: CalendarViewModel  by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

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
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth) // TODO Execute only once
    }

    private fun createCalendarDayBinder() = object : DayBinder<CalendarDayViewContainer> {
        override fun create(view: View) = CalendarDayViewContainer(view)

        override fun bind(container: CalendarDayViewContainer, day: CalendarDay) {
            container.day = day
            container.textView.text = day.getDayNumber()
            container.textView.setTextColor(day.getTextColor())

            if(day.isToday()) {
                container.setSelectedStyle()
            }

            container.setOnClickListener {
                viewModel.onDateClick(it.date.toCalendar())
            }
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

    private fun CalendarDay.isToday(): Boolean {
        val today = LocalDate.now(ZoneId.systemDefault())
        return date.isEqual(today)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}