package com.example.lifediary.ui.woman_section

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.lifediary.R
import com.example.lifediary.databinding.DialogDurationOfMenstrualCycleBinding
import com.example.lifediary.ui.common.UiConstants
import com.example.lifediary.utils.setDefaultButtonsStyle

class DurationOfMenstruationPeriodDialog : DialogFragment() {
    val viewModel: WomanSectionViewModel by activityViewModels()
    private var _binding: DialogDurationOfMenstrualCycleBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertDialog: AlertDialog

    companion object {
        const val FRAGMENT_TAG = "com.example.lifediary.ui.woman_section.DurationOfMenstruationPeriodDialog"
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
        binding.durationPicker.minValue = UiConstants.WomanSection.MIN_DURATION_OF_MENSTRUATION_PERIOD
        binding.durationPicker.maxValue = UiConstants.WomanSection.MAX_DURATION_OF_MENSTRUATION_PERIOD
        viewModel.durationOfMenstruationPeriod.value?.let { durationOfMenstruationPeriod ->
            setDurationPickerValue(durationOfMenstruationPeriod)
        }
    }

    private fun createDialog(): AlertDialog {
        alertDialog = AlertDialog
            .Builder(requireActivity())
            .setView(binding.root)
            .setPositiveButton(R.string.set) { _, _ ->
                val durationOfMenstruationPeriod = getDurationPickerValue()
                viewModel.onDurationOfMenstruationPeriodHasBeenSet(durationOfMenstruationPeriod)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                viewModel.onSetDurationOfMenstruationPeriodCancelled()
            }
            .create()
        return alertDialog
    }

    private fun getDurationPickerValue(): Int =
        binding.durationPicker.value

    private fun setDurationPickerValue(value: Int) {
        binding.durationPicker.value = value
    }

    override fun onResume() {
        super.onResume()
        alertDialog.setDefaultButtonsStyle()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}