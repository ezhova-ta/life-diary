package com.example.lifediary.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifediary.databinding.FragmentAddEditMainNoteBinding
import com.example.lifediary.ui.BaseFragment
import com.example.lifediary.utils.clearFocusWithKeyboard
import com.example.lifediary.utils.requestFocusWithKeyboard

class AddEditMainNoteFragment : BaseFragment() {
	override val viewModel: AddEditMainNoteViewModel by viewModels(
		factoryProducer = { AddEditMainNoteViewModel.Factory(getNoteIdFromArguments()) }
	)

	private var _binding: FragmentAddEditMainNoteBinding? = null
	private val binding get() = _binding!!

	companion object {
		const val NOTE_ID_KEY = "com.example.lifediary.ui.main.notes.NOTE_ID_KEY"
		const val DEFAULT_NOTE_ID_VALUE = 0L

		fun getInstance(noteId: Long?): AddEditMainNoteFragment {
			val fragment = AddEditMainNoteFragment()
			fragment.arguments = Bundle().apply{ putLong(NOTE_ID_KEY, noteId ?: DEFAULT_NOTE_ID_VALUE) }
			return fragment
		}
	}

	private fun getNoteIdFromArguments(): Long? {
		val noteId = requireArguments().getLong(NOTE_ID_KEY)
		if(noteId == DEFAULT_NOTE_ID_VALUE) return null
		return noteId
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAddEditMainNoteBinding.inflate(inflater, container, false)
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
 }