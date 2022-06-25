package com.example.lifediary.presentation.ui.calendar.date

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.lifediary.domain.utils.CalendarBuilder
import kotlinx.parcelize.Parcelize
import java.util.*

class ToDoListItemNotificationTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
	companion object {
		const val TAG = "com.example.lifediary.presentation.ui.calendar.date.ToDoListItemNotificationTimePickerFragment"
		const val PICK_TIME_REQUEST_KEY = "com.example.lifediary.presentation.ui.calendar.date.PICK_TIME_REQUEST_KEY"
		const val PICKED_TIME_BUNDLE_KEY = "com.example.lifediary.presentation.ui.calendar.date.PICKED_TIME_KEY"
	}

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val now = CalendarBuilder().build()
		val hour = now.get(Calendar.HOUR_OF_DAY)
		val minute = now.get(Calendar.MINUTE)
		return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
	}

	override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
		setFragmentResult(
			PICK_TIME_REQUEST_KEY,
			bundleOf(PICKED_TIME_BUNDLE_KEY to ToDoListItemNotificationTime(hourOfDay, minute))
		)
	}

	override fun onCancel(dialog: DialogInterface) {
		super.onCancel(dialog)
		setFragmentResult(
			PICK_TIME_REQUEST_KEY,
			bundleOf(PICKED_TIME_BUNDLE_KEY to null)
		)
	}

	@Parcelize
	data class ToDoListItemNotificationTime(val hour: Int, val minute: Int) : Parcelable
}