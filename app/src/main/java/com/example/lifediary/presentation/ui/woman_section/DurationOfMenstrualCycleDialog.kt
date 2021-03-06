package com.example.lifediary.presentation.ui.woman_section

import androidx.fragment.app.activityViewModels
import com.example.lifediary.presentation.ui.common.NumberPickerDialog

class DurationOfMenstrualCycleDialog: NumberPickerDialog() {
	val viewModel: WomanSectionViewModel by activityViewModels()

	companion object {
		const val FRAGMENT_TAG = "lifediary.presentation.ui.woman_section.DurationOfMenstrualCycleDialog"
		private const val MIN_DURATION_OF_MENSTRUAL_CYCLE = 15
		private const val MAX_DURATION_OF_MENSTRUAL_CYCLE = 50
	}

	override fun getMinValue() =
		MIN_DURATION_OF_MENSTRUAL_CYCLE

	override fun getMaxValue() =
		MAX_DURATION_OF_MENSTRUAL_CYCLE

	override fun getDefaultValue() =
		viewModel.durationOfMenstrualCycle.value

	override fun onNumberHasBeenSet(value: Int) {
		viewModel.onDurationOfMenstrualCycleHasBeenSet(value)
	}

	override fun onSetCancelled() {
		viewModel.onSetDurationOfMenstrualCycleCancelled()
	}
}