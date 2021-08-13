package com.example.lifediary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentCalendarDateBinding
import com.example.lifediary.ui.BaseFragment

class CalendarDateFragment : BaseFragment() {
    override val viewModel: CalendarDateViewModel  by viewModels()
    private var _binding: FragmentCalendarDateBinding? = null
    private val binding get() = _binding!!

//    companion object {
//        fun getInstance(): CalendarDateFragment() {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarDateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}