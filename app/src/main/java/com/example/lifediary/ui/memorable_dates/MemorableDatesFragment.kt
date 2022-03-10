package com.example.lifediary.ui.memorable_dates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.MemorableDateListAdapter
import com.example.lifediary.databinding.FragmentMemorableDatesBinding
import com.example.lifediary.ui.BaseFragment

class MemorableDatesFragment : BaseFragment() {
    override val viewModel: MemorableDatesViewModel by viewModels()
    private var _binding: FragmentMemorableDatesBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return MemorableDatesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemorableDatesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        setupMemorableDateListView()
        setupClearNoteListConfirmationDialog()
        setupDeleteDateConfirmationDialog()
    }

    private fun setupMemorableDateListView() {
        val memorableDateListAdapter = MemorableDateListAdapter(
            onEditItemClickListener = ListItemClickListener { viewModel.onEditDateClick(it) },
            onDeleteItemClickListener = ListItemClickListener { viewModel.onDeleteDateClick(it) },
            onItemLongClickListener = ListItemClickListener { viewModel.onDateLongClick(it) }
        )
        binding.memorableDateListView.adapter = memorableDateListAdapter
        viewModel.dates.observe(viewLifecycleOwner) { dateList ->
            memorableDateListAdapter.submitList(dateList)
        }
    }

    private fun setupClearNoteListConfirmationDialog() {
        viewModel.showClearDateListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearDateListConfirmationDialog()
        }
    }

    private fun showClearDateListConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.clear_memorable_dates_confirmation,
            positiveButtonTextRes = R.string.delete,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearDateListConfirmed,
            onCancelled = viewModel::onClearDateListCancelled
        )
    }

    private fun setupDeleteDateConfirmationDialog() {
        viewModel.showDeleteDateConfirmationDialog.observe(viewLifecycleOwner) { dateId ->
            if(dateId != null) showDeleteDateConfirmationDialog(dateId)
        }
    }

    private fun showDeleteDateConfirmationDialog(dateId: Long) {
        showConfirmationDialog(
            messageRes = R.string.delete_memorable_date_confirmation,
            positiveButtonTextRes = R.string.delete,
            negativeButtonRes = R.string.cancel,
            onConfirmed = { viewModel.onDeleteDateConfirmed(dateId) },
            onCancelled = viewModel::onDeleteDateCancelled
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}