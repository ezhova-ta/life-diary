package com.example.lifediary.presentation.ui.main.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.FragmentAddEditMainNoteBinding
import com.example.lifediary.presentation.ui.BaseFragment
import com.example.lifediary.presentation.utils.clearFocusWithKeyboard
import com.example.lifediary.presentation.utils.requestFocusWithKeyboard

class AddEditMainNoteFragment : BaseFragment() {
	override val viewModel: AddEditMainNoteViewModel by viewModels(
		factoryProducer = { AddEditMainNoteViewModel.Factory(getNoteIdFromArguments()) }
	)

	private var _binding: FragmentAddEditMainNoteBinding? = null
	private val binding get() = _binding!!

	companion object {
		private const val NOTE_ID_KEY = "lifediary.presentation.ui.main.notes.NOTE_ID_KEY"
		private const val DEFAULT_NOTE_ID = 0L

		fun getInstance(noteId: Long?): AddEditMainNoteFragment {
			val fragment = AddEditMainNoteFragment()
			fragment.arguments = Bundle().apply{ putLong(NOTE_ID_KEY, noteId ?: DEFAULT_NOTE_ID) }
			return fragment
		}
	}

	private fun getNoteIdFromArguments(): Long? {
		val noteId = requireArguments().getLong(NOTE_ID_KEY)
		if(noteId == DEFAULT_NOTE_ID) return null
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
		setupViews()
		return binding.root
	}

	private fun setupViews() {
		setupAddEditNoteInput()
		setupDeleteNoteConfirmationDialog()
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

	private fun setupDeleteNoteConfirmationDialog() {
		viewModel.showDeleteNoteConfirmationDialog.observe(viewLifecycleOwner) { noteId ->
			if(noteId != null) showDeleteNoteConfirmationDialog(noteId)
		}
	}

	private fun showDeleteNoteConfirmationDialog(noteId: Long) {
		showConfirmationDialog(
			messageRes = R.string.delete_note_confirmation,
			positiveButtonTextRes = R.string.delete,
			negativeButtonRes = R.string.cancel,
			onConfirmed = { viewModel.onDeleteNoteConfirmed(noteId) },
			onCancelled = viewModel::onDeleteNoteCancelled
		)
	}
 }