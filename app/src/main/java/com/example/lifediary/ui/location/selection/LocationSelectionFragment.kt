package com.example.lifediary.ui.location.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.LocationListAdapter
import com.example.lifediary.databinding.FragmentLocationSelectionBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.clearFocusWithKeyboard
import com.example.lifediary.utils.requestFocusWithKeyboard

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
        _binding = FragmentLocationSelectionBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupSearchLocationInput()
        setupLocationListRecycler()
        return binding.root
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