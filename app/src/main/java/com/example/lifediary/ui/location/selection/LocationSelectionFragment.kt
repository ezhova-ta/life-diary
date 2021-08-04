package com.example.lifediary.ui.location.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentLocationSelectionBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.clearFocusWithKeyboard
import com.example.lifediary.utils.requestFocusWithKeyboard

class LocationSelectionFragment : BaseFragment() {
    private val viewModel: LocationSelectionViewModel by viewModels()
    private var _binding: FragmentLocationSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationSelectionBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupSearchCityInput()
        return binding.root
    }

    private fun setupSearchCityInput() {
        viewModel.searchCityInputNeedsFocus.observe(viewLifecycleOwner) { needFocus ->
            if(needFocus) {
                binding.searchCityInput.requestFocusWithKeyboard()
            } else {
                binding.searchCityInput.clearFocusWithKeyboard(activity)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}