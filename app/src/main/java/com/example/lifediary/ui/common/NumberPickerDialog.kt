package com.example.lifediary.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.lifediary.R
import com.example.lifediary.databinding.NumberPickerDialogBinding
import com.example.lifediary.utils.setDefaultButtonsStyle

abstract class NumberPickerDialog: DialogFragment() {
    private var _binding: NumberPickerDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var alertDialog: AlertDialog

    abstract fun getMinValue(): Int
    abstract fun getMaxValue(): Int
    open fun getDefaultValue(): Int? = null
    open fun getTitleResId(): Int? = null
    open fun getOkButtonTextResId(): Int? = null
    open fun getCancelButtonTextResId(): Int? = null
    abstract fun onNumberHasBeenSet(value: Int)
    abstract fun onSetCancelled()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        inflateLayout()
        setupDurationPicker(getMinValue(), getMaxValue(), getDefaultValue())
        return createDialog(::onNumberHasBeenSet, ::onSetCancelled)
    }

    private fun inflateLayout() {
        _binding = NumberPickerDialogBinding.inflate(LayoutInflater.from(context))
    }

    private fun setupDurationPicker(
        minValue: Int,
        maxValue: Int,
        defaultValue: Int?
    ) {
        binding.numberPicker.minValue = minValue
        binding.numberPicker.maxValue = maxValue
        defaultValue?.let { setDurationPickerValue(it) }
    }

    private fun createDialog(
        onNumberHasBeenSet: (Int) -> Unit,
        onSetCancelled: () -> Unit
    ): AlertDialog {
        val dialogBuilder = AlertDialog
            .Builder(requireActivity())
            .setView(binding.root)
            .setPositiveButton(getOkButtonTextResId() ?: R.string.set) { _, _ ->
                onNumberHasBeenSet(getDurationPickerValue())
            }
            .setNegativeButton(getCancelButtonTextResId() ?: R.string.cancel) { _, _ ->
                onSetCancelled()
            }

        getTitleResId()?.let { titleResId ->
            dialogBuilder.setTitle(titleResId)
        }

        alertDialog = dialogBuilder.create()
        return alertDialog
    }

    private fun getDurationPickerValue(): Int =
        binding.numberPicker.value

    private fun setDurationPickerValue(value: Int) {
        binding.numberPicker.value = value
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