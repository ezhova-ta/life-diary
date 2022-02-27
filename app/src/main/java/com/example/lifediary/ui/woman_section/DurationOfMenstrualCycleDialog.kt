package com.example.lifediary.ui.woman_section

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.DialogDurationOfMenstrualCycleBinding

class DurationOfMenstrualCycleDialog: DialogFragment() {
	val viewModel: WomanSectionViewModel by activityViewModels()
	private var _binding: DialogDurationOfMenstrualCycleBinding? = null
	private val binding get() = _binding!!

	companion object {
		const val FRAGMENT_TAG = "com.example.lifediary.ui.woman_section.DurationOfMenstrualCycleDialog"
	}

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		inflateLayout()
		setupDurationPicker()
		return createDialog()
	}

	private fun inflateLayout() {
		_binding = DialogDurationOfMenstrualCycleBinding.inflate(LayoutInflater.from(context))
	}

	private fun setupDurationPicker() {
		binding.durationPicker.minValue = 15
		binding.durationPicker.maxValue = 50
		viewModel.durationOfMenstrualCycle.value?.let { durationOfMenstrualCycle ->
			setDurationPickerValue(durationOfMenstrualCycle)
		}
	}

	private fun createDialog(): AlertDialog {
		return AlertDialog
			.Builder(requireActivity())
			.setView(binding.root)
			.setPositiveButton(R.string.set) { _, _ ->
				val durationOfMenstrualCycle = getDurationPickerValue()
				viewModel.onDurationOfMenstrualCycleHasBeenSet(durationOfMenstrualCycle)
			}
			.setNegativeButton(R.string.cancel) { _, _ ->
				viewModel.onSetDurationOfMenstrualCycleCancelled()
			}
			.create()
	}

	private fun getDurationPickerValue(): Int =
		binding.durationPicker.value

	private fun setDurationPickerValue(value: Int) {
		binding.durationPicker.value = value
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}