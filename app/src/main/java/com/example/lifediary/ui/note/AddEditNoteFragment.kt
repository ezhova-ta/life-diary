package com.example.lifediary.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentAddEditNoteBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.Day

class AddEditNoteFragment : BaseFragment() {
    override val viewModel: AddEditNoteViewModel by viewModels(
        factoryProducer = { AddEditNoteViewModel.Factory(getDayFromArgument()) }
    )
    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_KEY = "com.example.lifediary.ui.note.TIME_IN_MILLIS_KEY"

        fun getInstance(day: Day): Fragment {
            val fragment = AddEditNoteFragment()
            fragment.arguments = Bundle().apply { putParcelable(DAY_KEY, day) }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
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