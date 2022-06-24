package com.example.lifediary.presentation.ui.woman_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.presentation.adapters.ListItemClickListener
import com.example.lifediary.presentation.adapters.MenstruationPeriodListAdapter
import com.example.lifediary.databinding.FragmentMenstruationPeriodListBinding
import com.example.lifediary.presentation.ui.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker

class MenstruationPeriodListFragment : BaseFragment() {
    override val viewModel: MenstruationPeriodListViewModel by viewModels()
    private var _binding: FragmentMenstruationPeriodListBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val DATE_PICKER_FRAGMENT_TAG = "com.example.lifediary.presentation.ui.woman_section.date_picker"

        fun getInstance(): Fragment {
            return MenstruationPeriodListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenstruationPeriodListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupMenstruationPeriodListRecycler()
        setupDeleteMenstruationPeriodConfirmationDialog()
        setupClearMenstruationPeriodListConfirmationDialog()
        setupMenstruationPeriodPickerShowing()
        return binding.root
    }

    private fun setupMenstruationPeriodListRecycler() {
        val menstruationPeriodListAdapter = MenstruationPeriodListAdapter(
            ListItemClickListener { viewModel.onDeleteMenstruationPeriodClick(it) }
        )
        binding.menstruationPeriodListView.adapter = menstruationPeriodListAdapter
        viewModel.menstruationPeriodList.observe(viewLifecycleOwner) { menstruationPeriodList ->
            menstruationPeriodListAdapter.submitList(menstruationPeriodList)
        }
    }

    private fun setupDeleteMenstruationPeriodConfirmationDialog() {
        viewModel.showDeleteMenstruationPeriodConfirmationDialog.observe(viewLifecycleOwner) { periodId ->
            if(periodId != null) showDeleteMenstruationPeriodConfirmationDialog(periodId)
        }
    }

    private fun showDeleteMenstruationPeriodConfirmationDialog(periodId: Long) {
        showConfirmationDialog(
            messageRes = R.string.delete_menstruation_period_confirmation,
            positiveButtonTextRes = R.string.delete,
            negativeButtonRes = R.string.cancel,
            onConfirmed = { viewModel.onDeleteMenstruationPeriodConfirmed(periodId) },
            onCancelled = viewModel::onDeleteMenstruationPeriodCancelled
        )
    }

    private fun setupClearMenstruationPeriodListConfirmationDialog() {
        viewModel.showClearMenstruationPeriodListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearMenstruationPeriodListConfirmationDialog()
        }
    }

    private fun showClearMenstruationPeriodListConfirmationDialog() {
        showConfirmationDialog(
            messageRes = R.string.clear_menstruation_period_list_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearMenstruationPeriodListConfirmed,
            onCancelled = viewModel::onClearMenstruationPeriodListCancelled
        )
    }

    private fun setupMenstruationPeriodPickerShowing() {
        viewModel.showMenstruationPeriodPicker.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showMenstruationPeriodPicker()
        }
    }

    private fun showMenstruationPeriodPicker() {
        MaterialDatePicker
            .Builder
            .dateRangePicker()
            .setTitleText(R.string.select_time_interval)
            .build()
            .apply {
                addOnPositiveButtonClickListener { period -> viewModel.onMenstruationPeriodSelected(period) }
                addOnNegativeButtonClickListener { viewModel.onMenstruationPeriodPickerCancelled() }
                addOnCancelListener { viewModel.onMenstruationPeriodPickerCancelled() }
                addOnDismissListener { viewModel.onMenstruationPeriodPickerCancelled() }
            }
            .show(requireActivity().supportFragmentManager,
                DATE_PICKER_FRAGMENT_TAG
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}