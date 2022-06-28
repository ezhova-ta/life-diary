package com.example.lifediary.presentation.ui.calendar.date.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentAddEditDateNoteBinding
import com.example.lifediary.presentation.ui.BaseFragment
import com.example.lifediary.domain.models.Day
import com.example.lifediary.presentation.ui.calendar.date.CalendarDateFragment
import com.example.lifediary.presentation.utils.clearFocusWithKeyboard
import com.example.lifediary.presentation.utils.requestFocusWithKeyboard

class AddEditDateNoteFragment : BaseFragment() {
    override val viewModel: AddEditDateNoteViewModel by viewModels(
        factoryProducer = { AddEditDateNoteViewModel.Factory(getDayFromArguments()) }
    )
    private var _binding: FragmentAddEditDateNoteBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_NUMBER_KEY = "lifediary.presentation.ui.calendar.date.note.DAY_NUMBER_KEY"
        private const val MONTH_NUMBER_KEY = "lifediary.presentation.ui.calendar.date.note.MONTH_NUMBER_KEY"
        private const val YEAR_KEY = "lifediary.presentation.ui.calendar.date.note.YEAR_KEY"

        fun getInstance(day: Day): Fragment {
            val fragment = AddEditDateNoteFragment()
            fragment.arguments = Bundle().apply {
                putInt(DAY_NUMBER_KEY, day.dayNumber)
                putInt(MONTH_NUMBER_KEY, day.monthNumber)
                putInt(YEAR_KEY, day.year)
            }
            return fragment
        }
    }

    private fun getDayFromArguments(): Day {
        val dayNumber = requireArguments().getInt(DAY_NUMBER_KEY)
        val monthNumber = requireArguments().getInt(MONTH_NUMBER_KEY)
        val year = requireArguments().getInt(YEAR_KEY)
        return Day(dayNumber, monthNumber, year)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditDateNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupAddEditNoteInput()
        return binding.root
    }

    private fun setupAddEditNoteInput() {
        viewModel.inputNeedsFocus.observe(viewLifecycleOwner) { needFocus ->
            if(needFocus) {
                binding.addNoteInput.requestFocusWithKeyboard()
            } else {
                binding.addNoteInput.clearFocusWithKeyboard(activity)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}