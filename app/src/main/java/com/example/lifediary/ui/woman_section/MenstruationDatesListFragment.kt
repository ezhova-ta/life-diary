package com.example.lifediary.ui.woman_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.MenstruationDatesListAdapter
import com.example.lifediary.databinding.FragmentMenstruationDatesListBinding
import com.example.lifediary.ui.BaseFragment

class MenstruationDatesListFragment : BaseFragment() {
    override val viewModel: MenstruationDatesListViewModel by viewModels()
    private var _binding: FragmentMenstruationDatesListBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return MenstruationDatesListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenstruationDatesListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupMenstruationDatesListRecycler()
        return binding.root
    }

    private fun setupMenstruationDatesListRecycler() {
        val menstruationDatesListAdapter = MenstruationDatesListAdapter(
            ListItemClickListener { viewModel.onDeleteMenstruationDatesClick(it) }
        )
        binding.menstruationDatesListView.adapter = menstruationDatesListAdapter
        viewModel.menstruationDatesList.observe(viewLifecycleOwner) { menstruationDatesList ->
            menstruationDatesListAdapter.submitList(menstruationDatesList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}