package com.example.lifediary.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.lifediary.utils.BroadcastConstants.ToDoListItemNotification.NOTIFICATION_TO_DO_LIST_ITEM_TEXT
import com.example.lifediary.utils.BroadcastConstants.ToDoListItemNotification.SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_ACTION

class AlarmReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		when(intent?.action) {
			SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_ACTION -> {
				intent.getStringExtra(NOTIFICATION_TO_DO_LIST_ITEM_TEXT)?.let { toDoListItemText ->
					showToDoListItemNotification(toDoListItemText)
				}
			}
		}
	}

	private fun showToDoListItemNotification(todoListItemText: String) {
		// TODO
	}
}