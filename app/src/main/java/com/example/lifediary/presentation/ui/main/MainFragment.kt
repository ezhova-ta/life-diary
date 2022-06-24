package com.example.lifediary.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentMainBinding
import com.example.lifediary.presentation.ui.BaseFragment
import com.example.lifediary.presentation.ui.activity.MainActivityViewModel

class MainFragment : BaseFragment() {
    override val viewModel: MainViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupNetworkConnectivityListener()
        return binding.root
    }

    private fun setupNetworkConnectivityListener() {
        activityViewModel.isNetworkConnectivityAvailable.observe(viewLifecycleOwner) { isAvailable ->
            binding.wiFiOffTextContainer.isVisible = !isAvailable
            viewModel.onAvailabilityOfNetworkConnectivityChanged(isAvailable)
        }
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