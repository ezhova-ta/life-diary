package com.example.lifediary.ui.woman_section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.databinding.FragmentWomanSectionBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.ui.woman_section.LengthOfMenstrualCycleDialog.Companion.FRAGMENT_TAG

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
        setupSetLengthOfMenstrualCycleDialog()
        return binding.root
    }

    private fun setupSetLengthOfMenstrualCycleDialog() {
        viewModel.showSetLengthOfMenstrualCycleDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow && !isSetLengthOfMenstrualCycleDialogCurrentlyDisplayed()) {
                showSetLengthOfMenstrualCycleDialog()
            }
        }
    }

    private fun showSetLengthOfMenstrualCycleDialog() {
        val fragmentManager = requireActivity().supportFragmentManager
        with(LengthOfMenstrualCycleDialog()) {
            isCancelable = false
            show(fragmentManager, FRAGMENT_TAG)
        }
    }

    private fun isSetLengthOfMenstrualCycleDialogCurrentlyDisplayed(): Boolean {
        return requireActivity().supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) != null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}