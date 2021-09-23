package com.example.lifediary

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.data.receivers.AlarmReceiver
import com.example.lifediary.utils.BroadcastConstants.ToDoListItemNotification.NOTIFICATION_TO_DO_LIST_ITEM_TEXT
import com.example.lifediary.utils.BroadcastConstants.ToDoListItemNotification.SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_ACTION
import com.example.lifediary.utils.BroadcastConstants.ToDoListItemNotification.SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_REQUEST_CODE
import java.util.*
import javax.inject.Inject

class NotificationScheduler @Inject constructor(private val context: Context) {
	fun scheduleNotification(toDoListItem: ToDoListItem, timeInMillis: Long? = null) {
		val alarmManager = getAlarmManager()
		val pendingIntent = createNotificationIntent(toDoListItem) ?: return
		alarmManager.setExact(
			AlarmManager.RTC_WAKEUP,
			timeInMillis ?: toDoListItem.getNotificationTimeInMillis(),
			pendingIntent
		)
	}

	private fun getAlarmManager(): AlarmManager {
		return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
	}

	private fun ToDoListItem.getNotificationTimeInMillis(): Long {
		// TODO Correct time
		val now = Calendar.getInstance().apply { add(Calendar.SECOND, 10) }
		return now.timeInMillis
	}

	private fun createNotificationIntent(toDoListItem: ToDoListItem): PendingIntent? {
		val intentData = toDoListItem.id?.toString()?.let { Uri.parse(it) } ?: return null
		val intent = Intent(context, AlarmReceiver::class.java).apply {
			action = SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_ACTION
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