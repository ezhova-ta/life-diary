package com.example.lifediary.data.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lifediary.Notifications.ToDoListChannel
import com.example.lifediary.R
import com.example.lifediary.utils.BroadcastConstants.ToDoListItemNotification.NOTIFICATION_TO_DO_LIST_ITEM_TEXT
import com.example.lifediary.utils.BroadcastConstants.ToDoListItemNotification.SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_ACTION

class AlarmReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		context ?: return

		when(intent?.action) {
			SCHEDULE_TO_DO_LIST_ITEM_NOTIFICATION_ACTION -> {
				intent.getStringExtra(NOTIFICATION_TO_DO_LIST_ITEM_TEXT)?.let { toDoListItemText ->
					showToDoListItemNotification(context, toDoListItemText)
				}
			}
		}
	}

	private fun showToDoListItemNotification(context: Context, todoListItemText: String) {
		val notificationId = todoListItemText.hashCode()
		val notification = createToDoListNotificationBuilder(context, todoListItemText).build()
		with(NotificationManagerCompat.from(context)) { notify(notificationId, notification) }
	}

	private fun createToDoListNotificationBuilder(context: Context, todoListItemText: String) =
		NotificationCompat.Builder(context, ToDoListChannel.id)
			.setSmallIcon(R.drawable.icon_notification_orange)
			.setContentTitle(context.getString(R.string.you_have_scheduled_for_today))
			.setContentText(todoListItemText)
			.setStyle(NotificationCompat.BigTextStyle()
				.bigText(todoListItemText))
			.setPriority(NotificationCompat.PRIORITY_HIGH)
}