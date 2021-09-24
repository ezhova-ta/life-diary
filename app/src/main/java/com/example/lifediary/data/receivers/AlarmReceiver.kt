package com.example.lifediary.data.receivers

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lifediary.Notifications.ToDoListChannel
import com.example.lifediary.R
import com.example.lifediary.ui.activity.MainActivity
import com.example.lifediary.utils.IntentConstants.ToDoListItemNotification.ACTION_SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION
import com.example.lifediary.utils.IntentConstants.ToDoListItemNotification.ACTION_SHOW_CURRENT_CALENDAR_DAY
import com.example.lifediary.utils.IntentConstants.ToDoListItemNotification.NOTIFICATION_TO_DO_LIST_ITEM_TEXT
import com.example.lifediary.utils.IntentConstants.ToDoListItemNotification.SHOW_CURRENT_CALENDAR_DAY_REQUEST_CODE

class AlarmReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		context ?: return

		when(intent?.action) {
			ACTION_SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION -> {
				intent.getStringExtra(NOTIFICATION_TO_DO_LIST_ITEM_TEXT)?.let { toDoListItemText ->
					showToDoListItemNotification(context, toDoListItemText)
				}
			}
		}
	}

	private fun showToDoListItemNotification(context: Context, todoListItemText: String) {
		val notificationId = todoListItemText.hashCode()
		val notification = createToDoListNotification(context, todoListItemText)
		with(NotificationManagerCompat.from(context)) { notify(notificationId, notification) }
	}

	private fun createToDoListNotification(context: Context, todoListItemText: String): Notification {
		return NotificationCompat.Builder(context, ToDoListChannel.id)
			.setSmallIcon(R.drawable.icon_notification_orange)
			.setContentTitle(context.getString(R.string.you_have_scheduled_for_today))
			.setContentText(todoListItemText)
			.setStyle(NotificationCompat.BigTextStyle().bigText(todoListItemText))
			.setPriority(NotificationCompat.PRIORITY_HIGH)
			.setContentIntent(createShowCurrentToDoListIntent(context))
			.setAutoCancel(true)
			.build()
	}

	private fun createShowCurrentToDoListIntent(context: Context): PendingIntent {
		val intent = Intent(context, MainActivity::class.java).apply {
			flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
			action = ACTION_SHOW_CURRENT_CALENDAR_DAY
		}
		return PendingIntent.getActivity(
			context,
			SHOW_CURRENT_CALENDAR_DAY_REQUEST_CODE,
			intent,
			FLAG_UPDATE_CURRENT
		)
	}
}