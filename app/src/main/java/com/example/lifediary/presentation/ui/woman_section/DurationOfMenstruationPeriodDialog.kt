package com.example.lifediary.presentation.ui.woman_section

import androidx.fragment.app.activityViewModels
import com.example.lifediary.presentation.ui.common.NumberPickerDialog
import com.example.lifediary.presentation.utils.UiConstants

class DurationOfMenstruationPeriodDialog : NumberPickerDialog() {
    val viewModel: WomanSectionViewModel by activityViewModels()

    companion object {
        const val FRAGMENT_TAG = "com.example.lifediary.presentation.ui.woman_section.DurationOfMenstruationPeriodDialog"
        private const val MIN_DURATION_OF_MENSTRUATION_PERIOD = 1
        private const val MAX_DURATION_OF_MENSTRUATION_PERIOD = 10
    }

    override fun getMinValue() =
        MIN_DURATION_OF_MENSTRUATION_PERIOD

    override fun getMaxValue() =
        MAX_DURATION_OF_MENSTRUATION_PERIOD

    override fun getDefaultValue() =
        viewModel.durationOfMenstruationPeriod.value

    override fun onNumberHasBeenSet(value: Int) {
        viewModel.onDurationOfMenstruationPeriodHasBeenSet(value)
    }

    override fun onSetCancelled() {
        viewModel.onSetDurationOfMenstruationPeriodCancelled()
    }
}