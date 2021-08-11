package com.example.lifediary.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentMainBinding
import com.example.lifediary.ui.BaseFragment

class MainFragment : BaseFragment() {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupCurrentWeatherUpdatingErrorShowing()
        return binding.root
    }

    private fun setupCurrentWeatherUpdatingErrorShowing() {
        viewModel.showCurrentWeatherUpdatingErrorEvent.observe(viewLifecycleOwner) { event ->
            if(event.shouldBeHandled()) {
                showCurrentWeatherUpdatingError()
            }
        }
    }

    // TODO Move to base fragment
    private fun showCurrentWeatherUpdatingError() {
        Toast.makeText(
            requireActivity(),
            resources.getText(R.string.failed_to_update_weather_data),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onScreenResumed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}