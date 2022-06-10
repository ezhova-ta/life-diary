package com.example.lifediary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentCalendarBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.dates.toDomain
import java.time.YearMonth

class CalendarFragment : BaseFragment() {
    override val viewModel: CalendarViewModel  by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var lastScrolledMonth: YearMonth = YearMonth.now()

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
        val calendarDrawer: BaseCalendarDrawer = StandardCalendarDrawer(binding.calendarView)
        calendarDrawer.initialize { viewModel.onDateClick(it.toDomain()) }
        calendarDrawer.onScrollMonthListener = { lastScrolledMonth = it }
        calendarDrawer.scrollToMonth(lastScrolledMonth)

        viewModel.calendarDaysData.observe(viewLifecycleOwner) {
            it ?: return@observe
            calendarDrawer.onDataChanged(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}