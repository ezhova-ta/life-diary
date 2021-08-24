package com.example.lifediary.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentAddEditNotesBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.Day

class AddEditNotesFragment : BaseFragment() {
    override val viewModel: AddEditNotesViewModel by viewModels(
        factoryProducer = { AddEditNotesViewModel.Factory(getDayFromArgument()) }
    )
    private var _binding: FragmentAddEditNotesBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_KEY = "com.example.lifediary.ui.notes.TIME_IN_MILLIS_KEY"

        fun getInstance(day: Day): Fragment {
            val fragment = AddEditNotesFragment()
            fragment.arguments = Bundle().apply { putParcelable(DAY_KEY, day) }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNotesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    private fun getDayFromArgument(): Day {
        return requireArguments().getParcelable(DAY_KEY)!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}