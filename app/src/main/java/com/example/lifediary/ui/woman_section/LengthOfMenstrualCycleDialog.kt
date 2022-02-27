package com.example.lifediary.ui.woman_section

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.DialogLengthOfMenstrualCycleBinding

class LengthOfMenstrualCycleDialog: DialogFragment() {
	val viewModel: WomanSectionViewModel by activityViewModels()
	private var _binding: DialogLengthOfMenstrualCycleBinding? = null
	private val binding get() = _binding!!

	companion object {
		const val FRAGMENT_TAG = "com.example.lifediary.ui.woman_section.LengthOfMenstrualCycleDialog"
	}

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		inflateLayout()
		setupLengthPicker()
		return createDialog()
	}

	private fun inflateLayout() {
		_binding = DialogLengthOfMenstrualCycleBinding.inflate(LayoutInflater.from(context))
	}

	private fun setupLengthPicker() {
		binding.lengthPicker.minValue = 15
		binding.lengthPicker.maxValue = 50
	}

	private fun createDialog(): AlertDialog {
		return AlertDialog
			.Builder(requireActivity())
			.setView(binding.root)
			.setPositiveButton(R.string.set) { _, _ ->
				val lengthOfMenstrualCycle = getLengthPickerValue()
				viewModel.onLengthOfMenstrualCycleHasBeenSet(lengthOfMenstrualCycle)
			}
			.setNegativeButton(R.string.cancel) { _, _ ->
				viewModel.onSetLengthOfMenstrualCycleCancelled()
			}
			.create()
	}

	private fun getLengthPickerValue(): Int =
		binding.lengthPicker.value

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}