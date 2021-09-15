package com.example.lifediary.ui.calendar.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.ToDoListAdapter
import com.example.lifediary.databinding.FragmentCalendarDateBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.Day

class CalendarDateFragment : BaseFragment() {
    override val viewModel: CalendarDateViewModel by viewModels(
        factoryProducer = { CalendarDateViewModel.Factory(getDayFromArguments()) }
    )
    private var _binding: FragmentCalendarDateBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_KEY = "com.example.lifediary.ui.calendar.DAY_KEY"
        private const val NOTE_VIEW_ROLLED_UP_MAX_LINES = 5

        fun getInstance(day: Day): Fragment {
            val fragment = CalendarDateFragment()
            fragment.arguments = Bundle().apply { putParcelable(DAY_KEY, day) }
            return fragment
        }
    }

    private fun getDayFromArguments(): Day {
        return requireArguments().getParcelable(DAY_KEY)!!
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
        setupClearToDOListConfirmationDialog()
        return binding.root
    }

    private fun setupNoteView() {
        binding.noteView.rolledUpMaxLines = NOTE_VIEW_ROLLED_UP_MAX_LINES
        binding.noteView.setOnLongClickListener {
            viewModel.onNoteLongClick()
            true
        }
    }

    private fun setupToDoListView() {
        val toDoListAdapter = ToDoListAdapter(
            onDeleteItemClickListener = ListItemClickListener { viewModel.onDeleteToDoListItemClick(it) },
            onItemClickListener = ListItemClickListener { viewModel.onToDoListItemClick(it) },
            onItemLongClickListener = ListItemClickListener { viewModel.onToDoListItemLongClick(it) }
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

    private fun setupClearToDOListConfirmationDialog() {
        viewModel.showClearToDoListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearToDOListConfirmationDialog()
        }
    }

    private fun showClearToDOListConfirmationDialog() {
        showDefaultConfirmationDialog(
            messageRes = R.string.clear_to_do_list_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearToDoListConfirmed,
            onCancelled = viewModel::onClearToDoListCancelled
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}