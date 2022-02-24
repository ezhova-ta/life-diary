package com.example.lifediary.ui.woman_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.MenstruationDatesListAdapter
import com.example.lifediary.databinding.FragmentMenstruationDatesListBinding
import com.example.lifediary.ui.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker

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
        setupDeleteMenstruationDatesConfirmationDialog()
        setupClearMenstruationDatesListConfirmationDialog()
        setupMenstruationDatesPickerShowing()
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

    private fun setupDeleteMenstruationDatesConfirmationDialog() {
        viewModel.showDeleteMenstruationDatesConfirmationDialog.observe(viewLifecycleOwner) { datesId ->
            if(datesId != null) showDeleteMenstruationDatesConfirmationDialog(datesId)
        }
    }

    private fun showDeleteMenstruationDatesConfirmationDialog(datesId: Long) {
        showDefaultConfirmationDialog(
            messageRes = R.string.delete_menstruation_dates_confirmation,
            positiveButtonTextRes = R.string.delete,
            negativeButtonRes = R.string.cancel,
            onConfirmed = { viewModel.onDeleteMenstruationDatesConfirmed(datesId) },
            onCancelled = viewModel::onDeleteMenstruationDatesCancelled
        )
    }

    private fun setupClearMenstruationDatesListConfirmationDialog() {
        viewModel.showClearMenstruationDatesListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearMenstruationDatesListConfirmationDialog()
        }
    }

    private fun showClearMenstruationDatesListConfirmationDialog() {
        showDefaultConfirmationDialog(
            messageRes = R.string.clear_menstruation_dates_list_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearMenstruationDatesListConfirmed,
            onCancelled = viewModel::onClearMenstruationDatesListCancelled
        )
    }

    private fun setupMenstruationDatesPickerShowing() {
        viewModel.showMenstruationDatesPicker.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showMenstruationDatesPicker()
        }
    }

    private fun showMenstruationDatesPicker() {
        MaterialDatePicker
            .Builder
            .dateRangePicker()
            .setTitleText(R.string.select_time_interval)
            .build()
            .apply {
                addOnPositiveButtonClickListener { dates -> viewModel.onMenstruationDatesSelected(dates) }
                addOnNegativeButtonClickListener { viewModel.onMenstruationDatesPickerCancelled() }
                addOnCancelListener { viewModel.onMenstruationDatesPickerCancelled() }
                addOnDismissListener { viewModel.onMenstruationDatesPickerCancelled() }
            }
            .show(requireActivity().supportFragmentManager,
                WomanSectionFragment.DATE_PICKER_FRAGMENT_TAG
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}