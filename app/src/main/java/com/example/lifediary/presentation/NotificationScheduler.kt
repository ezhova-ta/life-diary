package com.example.lifediary.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.presentation.utils.IntentConstants.ToDoListItemNotification.ACTION_SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION
import com.example.lifediary.presentation.utils.IntentConstants.ToDoListItemNotification.NOTIFICATION_TO_DO_LIST_ITEM_TEXT
import com.example.lifediary.presentation.utils.IntentConstants.ToDoListItemNotification.SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_REQUEST_CODE
import javax.inject.Inject

class NotificationScheduler @Inject constructor(private val context: Context) {
	fun scheduleNotification(toDoListItem: ToDoListItem, timeInMillis: Long) {
		val alarmManager = getAlarmManager()
		val intent = createNotificationIntent(toDoListItem) ?: return
		alarmManager.setExact(
			AlarmManager.RTC_WAKEUP,
			timeInMillis,
			intent
		)
	}

	fun cancelScheduledNotification(toDoListItem: ToDoListItem) {
		val alarmManager = getAlarmManager()
		val intent = createNotificationIntent(toDoListItem) ?: return
		alarmManager.cancel(intent)
	}

	private fun getAlarmManager(): AlarmManager {
		return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
	}

	private fun createNotificationIntent(toDoListItem: ToDoListItem): PendingIntent? {
		val intentData = toDoListItem.id?.toString()?.let { Uri.parse(it) } ?: return null
		val intent = Intent(context, AlarmReceiver::class.java).apply {
			action = ACTION_SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION
			data = intentData
			putExtra(NOTIFICATION_TO_DO_LIST_ITEM_TEXT, toDoListItem.text)
		}

		return PendingIntent.getBroadcast(
			context,
			SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_REQUEST_CODE,
			intent,
			PendingIntent.FLAG_CANCEL_CURRENT
		)
	}
}