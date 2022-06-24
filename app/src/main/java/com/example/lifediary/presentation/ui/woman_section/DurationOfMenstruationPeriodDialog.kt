package com.example.lifediary.presentation.ui.woman_section

import androidx.fragment.app.activityViewModels
import com.example.lifediary.presentation.ui.common.NumberPickerDialog
import com.example.lifediary.presentation.utils.UiConstants

class DurationOfMenstruationPeriodDialog : NumberPickerDialog() {
    val viewModel: WomanSectionViewModel by activityViewModels()

    companion object {
        const val FRAGMENT_TAG = "com.example.lifediary.presentation.ui.woman_section.DurationOfMenstruationPeriodDialog"
    }

    override fun getMinValue() =
        UiConstants.WomanSection.MIN_DURATION_OF_MENSTRUATION_PERIOD

    override fun getMaxValue() =
        UiConstants.WomanSection.MAX_DURATION_OF_MENSTRUATION_PERIOD

    override fun getDefaultValue() =
        viewModel.durationOfMenstruationPeriod.value

    override fun onNumberHasBeenSet(value: Int) {
        viewModel.onDurationOfMenstruationPeriodHasBeenSet(value)
    }

    override fun onSetCancelled() {
        viewModel.onSetDurationOfMenstruationPeriodCancelled()
    }
}