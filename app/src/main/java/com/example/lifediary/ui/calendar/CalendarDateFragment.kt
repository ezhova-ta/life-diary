package com.example.lifediary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentCalendarDateBinding
import com.example.lifediary.ui.BaseFragment
import java.util.*

class CalendarDateFragment : BaseFragment() {
    override val viewModel: CalendarDateViewModel  by viewModels()
    private var _binding: FragmentCalendarDateBinding? = null
    private val binding get() = _binding!!
    private lateinit var date: Calendar

    companion object {
        private const val TIME_IN_MILLIS_KEY = "com.example.lifediary.ui.calendar.TIME_IN_MILLIS_KEY"

        fun getInstance(timeInMillis: Long): Fragment {
            val fragment = CalendarDateFragment()
            fragment.arguments = Bundle().apply { putLong(TIME_IN_MILLIS_KEY, timeInMillis) }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val timeInMillis = requireArguments().getLong(TIME_IN_MILLIS_KEY)
        date = Calendar.getInstance().apply { this.timeInMillis = timeInMillis }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}