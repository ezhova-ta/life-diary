package com.example.lifediary.ui.woman_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentWomanSectionBinding
import com.example.lifediary.ui.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker

class WomanSectionFragment : BaseFragment() {
    override val viewModel: WomanSectionViewModel by viewModels()
    private var _binding: FragmentWomanSectionBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val DATE_PICKER_FRAGMENT_TAG = "com.example.lifediary.ui.woman_section.date_picker"

        fun getInstance(): Fragment {
            return WomanSectionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWomanSectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupMenstruationDatesPickerShowing()
        return binding.root
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
                addOnNegativeButtonClickListener { viewModel.onCancelMenstruationDatesPicker() }
                addOnCancelListener { viewModel.onCancelMenstruationDatesPicker() }
                addOnDismissListener { viewModel.onCancelMenstruationDatesPicker() }
            }
            .show(requireActivity().supportFragmentManager, DATE_PICKER_FRAGMENT_TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}