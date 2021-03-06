package com.example.lifediary.presentation.ui.woman_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.databinding.FragmentWomanSectionBinding
import com.example.lifediary.presentation.ui.BaseFragment

class WomanSectionFragment : BaseFragment() {
    override val viewModel: WomanSectionViewModel by activityViewModels()
    private var _binding: FragmentWomanSectionBinding? = null
    private val binding get() = _binding!!

    companion object {
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
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        setupSetDurationOfMenstrualCycleDialog()
        setupSetDurationOfMenstruationPeriodDialog()
    }

    private fun setupSetDurationOfMenstrualCycleDialog() {
        viewModel.showSetDurationOfMenstrualCycleDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow && !isSetDurationOfMenstrualCycleDialogCurrentlyDisplayed()) {
                showSetDurationOfMenstrualCycleDialog()
            }
        }
    }

    private fun setupSetDurationOfMenstruationPeriodDialog() {
        viewModel.showSetDurationOfMenstruationPeriodDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow && !isSetDurationOfMenstruationPeriodDialogCurrentlyDisplayed()) {
                showSetDurationOfMenstruationPeriodDialog()
            }
        }
    }

    private fun showSetDurationOfMenstrualCycleDialog() {
        val dialog = DurationOfMenstrualCycleDialog()
        dialog.isCancelable = false
        dialog.show(childFragmentManager, DurationOfMenstrualCycleDialog.FRAGMENT_TAG)
    }

    private fun showSetDurationOfMenstruationPeriodDialog() {
        val dialog = DurationOfMenstruationPeriodDialog()
        dialog.isCancelable = false
        dialog.show(childFragmentManager, DurationOfMenstruationPeriodDialog.FRAGMENT_TAG)
    }

    private fun isSetDurationOfMenstrualCycleDialogCurrentlyDisplayed(): Boolean {
        return childFragmentManager.findFragmentByTag(DurationOfMenstrualCycleDialog.FRAGMENT_TAG) != null
    }

    private fun isSetDurationOfMenstruationPeriodDialogCurrentlyDisplayed(): Boolean {
        return childFragmentManager.findFragmentByTag(DurationOfMenstruationPeriodDialog.FRAGMENT_TAG) != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}