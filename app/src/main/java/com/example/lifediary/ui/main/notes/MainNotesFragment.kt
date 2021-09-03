package com.example.lifediary.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.MainNoteListAdapter
import com.example.lifediary.databinding.FragmentMainNotesBinding
import com.example.lifediary.ui.BaseFragment

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
        setupNoteListView()
        return binding.root
    }

    private fun setupNoteListView() {
        val mainNoteListAdapter = MainNoteListAdapter(
            ListItemClickListener { viewModel.onEditNoteClick(it) },
            ListItemClickListener { viewModel.onDeleteNoteClick(it) }
        )
        binding.noteListView.adapter = mainNoteListAdapter
        viewModel.noteList.observe(viewLifecycleOwner) { noteList ->
            mainNoteListAdapter.submitList(noteList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}