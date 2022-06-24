package com.example.lifediary.presentation.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.presentation.adapters.ListItemClickListener
import com.example.lifediary.presentation.adapters.MainNoteListAdapter
import com.example.lifediary.presentation.MainNoteListSortMethodDropDownItem
import com.example.lifediary.databinding.FragmentMainNotesBinding
import com.example.lifediary.presentation.ui.BaseFragment

class MainNotesFragment : BaseFragment() {
    override val viewModel: MainNotesViewModel by viewModels()
    private var _binding: FragmentMainNotesBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return MainNotesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainNotesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        setupNoteListView()
        setupClearNoteListConfirmationDialog()
        setupSortMethodDropDown()
    }

    private fun setupNoteListView() {
        val mainNoteListAdapter = MainNoteListAdapter(
            ListItemClickListener { viewModel.onNoteLongClick(it) }
        )
        binding.noteListView.adapter = mainNoteListAdapter
        viewModel.noteList.observe(viewLifecycleOwner) { noteList ->
            mainNoteListAdapter.submitList(noteList)
        }
    }

    private fun setupClearNoteListConfirmationDialog() {
        viewModel.showClearNoteListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearNoteListConfirmationDialog()
        }
    }

    private fun showClearNoteListConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.clear_note_list_confirmation,
            positiveButtonTextRes = R.string.delete,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearNoteListConfirmed,
            onCancelled = viewModel::onClearNoteListCancelled
        )
    }

    private fun setupSortMethodDropDown() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.sort_method_spinner_item,
            MainNoteListSortMethodDropDownItem.getAllStrings(requireContext())
        )

        binding.sortMethodDropDown.adapter = adapter

        binding.sortMethodDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sortMethod = MainNoteListSortMethodDropDownItem.getFromPosition(position)
                viewModel.onSortMethodSelected(sortMethod)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.noteListSortMethodId.observe(viewLifecycleOwner) { sortMethodId ->
            sortMethodId ?: return@observe
            val position = MainNoteListSortMethodDropDownItem.getPositionFromId(sortMethodId)
            binding.sortMethodDropDown.setSelection(position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}