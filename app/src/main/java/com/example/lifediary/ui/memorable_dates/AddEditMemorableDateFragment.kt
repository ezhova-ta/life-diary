package com.example.lifediary.ui.memorable_dates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentAddEditMemorableDateBinding
import com.example.lifediary.ui.BaseFragment

class AddEditMemorableDateFragment : BaseFragment() {
	override val viewModel: AddEditMemorableDateViewModel by viewModels(
		factoryProducer = { AddEditMemorableDateViewModel.Factory(getDateIdFromArguments()) }
	)

	private var _binding: FragmentAddEditMemorableDateBinding? = null
	private val binding get() = _binding!!

	companion object {
		const val DATE_ID_KEY = "com.example.lifediary.ui.main.notes.NOTE_ID_KEY"
		const val DEFAULT_DATE_ID_VALUE = 0L

		fun getInstance(noteId: Long?): AddEditMemorableDateFragment {
			val fragment = AddEditMemorableDateFragment()
			fragment.arguments = Bundle().apply{ putLong(DATE_ID_KEY, noteId ?: DEFAULT_DATE_ID_VALUE) }
			return fragment
		}
	}

	private fun getDateIdFromArguments(): Long? {
		val noteId = requireArguments().getLong(DATE_ID_KEY)
		if(noteId == DEFAULT_DATE_ID_VALUE) return null
		return noteId
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAddEditMemorableDateBinding.inflate(inflater, container, false)
		binding.lifecycleOwner = viewLifecycleOwner
		binding.viewModel = viewModel
		return binding.root
	}
 }