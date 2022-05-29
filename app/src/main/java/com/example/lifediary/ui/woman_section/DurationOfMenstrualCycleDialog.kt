package com.example.lifediary.ui.woman_section

import androidx.fragment.app.activityViewModels
import com.example.lifediary.ui.common.NumberPickerDialog
import com.example.lifediary.utils.UiConstants

class DurationOfMenstrualCycleDialog: NumberPickerDialog() {
	val viewModel: WomanSectionViewModel by activityViewModels()

	companion object {
		const val FRAGMENT_TAG = "com.example.lifediary.ui.woman_section.DurationOfMenstrualCycleDialog"
	}

	override fun getMinValue() =
		UiConstants.WomanSection.MIN_DURATION_OF_MENSTRUAL_CYCLE

	override fun getMaxValue() =
		UiConstants.WomanSection.MAX_DURATION_OF_MENSTRUAL_CYCLE

	override fun getDefaultValue() =
		viewModel.durationOfMenstrualCycle.value

	override fun onNumberHasBeenSet(value: Int) {
		viewModel.onDurationOfMenstrualCycleHasBeenSet(value)
	}

	override fun onSetCancelled() {
		viewModel.onSetDurationOfMenstrualCycleCancelled()
	}
}