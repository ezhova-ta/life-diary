package com.example.lifediary.ui.calendar.date.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentAddEditDateNoteBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.data.domain.Day
import com.example.lifediary.utils.clearFocusWithKeyboard
import com.example.lifediary.utils.requestFocusWithKeyboard

class AddEditDateNoteFragment : BaseFragment() {
    override val viewModel: AddEditDateNoteViewModel by viewModels(
        factoryProducer = { AddEditDateNoteViewModel.Factory(getDayFromArguments()) }
    )
    private var _binding: FragmentAddEditDateNoteBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val DAY_KEY = "com.example.lifediary.ui.calendar.date.note.DAY_KEY"

        fun getInstance(day: Day): Fragment {
            val fragment = AddEditDateNoteFragment()
            fragment.arguments = Bundle().apply { putParcelable(DAY_KEY, day) }
            return fragment
        }
    }

    private fun getDayFromArguments(): Day {
        return requireArguments().getParcelable(DAY_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditDateNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupAddEditNoteInput()
        return binding.root
    }

    private fun setupAddEditNoteInput() {
        viewModel.inputNeedsFocus.observe(viewLifecycleOwner) { needFocus ->
            if(needFocus) {
                binding.addNoteInput.requestFocusWithKeyboard()
            } else {
                binding.addNoteInput.clearFocusWithKeyboard(activity)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}