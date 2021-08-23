package com.example.lifediary.ui.calendar.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentCalendarDateBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.Day

class CalendarDateFragment : BaseFragment() {
    override val viewModel: CalendarDateViewModel by viewModels(
        factoryProducer = { CalendarDateViewModel.Factory(getDayFromArgument()) }
    )
    private var _binding: FragmentCalendarDateBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_KEY = "com.example.lifediary.ui.calendar.TIME_IN_MILLIS_KEY"
        private const val NOTES_VIEW_ROLLED_UP_MAX_LINES = 5

        fun getInstance(day: Day): Fragment {
            val fragment = CalendarDateFragment()
            fragment.arguments = Bundle().apply { putParcelable(DAY_KEY, day) }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setUpNotesView()
        return binding.root
    }

    private fun setUpNotesView() {
        binding.notesView.rolledUpMaxLines = NOTES_VIEW_ROLLED_UP_MAX_LINES
    }

    private fun getDayFromArgument(): Day {
        return requireArguments().getParcelable(DAY_KEY)!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}