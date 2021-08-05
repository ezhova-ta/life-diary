package com.example.lifediary.ui.location.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifediary.adapters.LocationListAdapter
import com.example.lifediary.adapters.OnLocationListItemClickListener
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
        setupSearchLocationInput()
        setupLocationListRecycler()
        return binding.root
    }

    private fun setupSearchLocationInput() {
        viewModel.searchLocationInputNeedsFocus.observe(viewLifecycleOwner) { needFocus ->
            if(needFocus) {
                binding.searchLocationInput.requestFocusWithKeyboard()
            } else {
                binding.searchLocationInput.clearFocusWithKeyboard(activity)
            }
        }
    }

    private fun setupLocationListRecycler() {
        val locationListAdapter = LocationListAdapter(
            OnLocationListItemClickListener { viewModel.onLocationListItemClick(it) }
        )
        binding.locationListView.adapter = locationListAdapter
        viewModel.locations.observe(viewLifecycleOwner) { locations ->
            locationListAdapter.submitList(locations)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}