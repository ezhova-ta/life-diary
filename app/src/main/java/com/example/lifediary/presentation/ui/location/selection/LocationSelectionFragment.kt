package com.example.lifediary.presentation.ui.location.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentLocationSelectionBinding
import com.example.lifediary.domain.models.ProposedLocation.*
import com.example.lifediary.presentation.adapters.ListItemClickListener
import com.example.lifediary.presentation.adapters.LocationListAdapter
import com.example.lifediary.presentation.ui.BaseFragment
import com.example.lifediary.presentation.utils.clearFocusWithKeyboard
import com.example.lifediary.presentation.utils.requestFocusWithKeyboard

class LocationSelectionFragment : BaseFragment() {
    override val viewModel: LocationSelectionViewModel by viewModels()
    private var _binding: FragmentLocationSelectionBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return LocationSelectionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationSelectionBinding.inflate(
            layoutInflater,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupProposedLocationButtons()
        setupSearchLocationInput()
        setupLocationListRecycler()
        return binding.root
    }

    private fun setupProposedLocationButtons() {
        binding.saintPetersburgButton.setOnClickListener {
            viewModel.onProposedLocationClick(SAINT_PETERSBURG)
        }

        binding.moscowButton.setOnClickListener {
            viewModel.onProposedLocationClick(MOSCOW)
        }
    }

    private fun setupSearchLocationInput() {
        viewModel.inputNeedsFocus.observe(viewLifecycleOwner) { needFocus ->
            if(needFocus) {
                binding.searchLocationInput.requestFocusWithKeyboard()
            } else {
                binding.searchLocationInput.clearFocusWithKeyboard(activity)
            }
        }

        binding.searchLocationInput.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.searchLocationInput.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onSearchLocationInputDone()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun setupLocationListRecycler() {
        val locationListAdapter = LocationListAdapter(
            ListItemClickListener { viewModel.onLocationListItemClick(it) }
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