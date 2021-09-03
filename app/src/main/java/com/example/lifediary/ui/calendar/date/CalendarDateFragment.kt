package com.example.lifediary.ui.calendar.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.ToDoListAdapter
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
        private const val NOTE_VIEW_ROLLED_UP_MAX_LINES = 5

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
        setupNoteView()
        setupToDoListView()
        setupAddToDoListItemInputView()
        return binding.root
    }

    private fun setupNoteView() {
        binding.noteView.rolledUpMaxLines = NOTE_VIEW_ROLLED_UP_MAX_LINES
    }

    private fun setupToDoListView() {
        val toDoListAdapter = ToDoListAdapter(
            ListItemClickListener { viewModel.onToDoListItemClick(it) },
            ListItemClickListener { viewModel.onDeleteToDoListItemClick(it) }
        )
        binding.toDoListView.adapter = toDoListAdapter
        viewModel.toDoList.observe(viewLifecycleOwner) { toDoList ->
            toDoListAdapter.submitList(toDoList)
        }
    }

    private fun setupAddToDoListItemInputView() {
        binding.addToDoListItemInput.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.addToDoListItemInput.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onAddToDoListItemInputDone()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun getDayFromArgument(): Day {
        return requireArguments().getParcelable(DAY_KEY)!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}