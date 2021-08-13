package com.example.lifediary.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentCalendarBinding
import com.example.lifediary.ui.BaseFragment
import org.joda.time.DateTime
import java.util.*

class CalendarFragment : BaseFragment() {
    override val viewModel: CalendarViewModel  by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

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
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            val date = Calendar.getInstance()
//            date.set(year, month, dayOfMonth)
            viewModel.onDateClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}