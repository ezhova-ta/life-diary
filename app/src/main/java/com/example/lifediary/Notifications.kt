package com.example.lifediary

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.annotation.StringRes

object Notifications {
	private const val TO_DO_LIST_CHANNEL_ID = "com.example.lifediary.TO_DO_LIST_CHANNEL"
	@StringRes private const val TO_DO_LIST_CHANNEL_NAME = R.string.to_do_list_channel

	fun ensureChannel(context: Context, channel: Channel) {
		createNotificationChannel(context, channel)
	}

	private fun createNotificationChannel(context: Context, channel: Channel) {
		val notificationChannel = NotificationChannel(
			channel.id,
			context.getString(channel.nameRes),
			channel.importance
		).apply {
			description = context.getString(channel.descriptionRes)
			lockscreenVisibility = channel.lockScreenVisibility
		}

		getNotificationManager(context).createNotificationChannel(notificationChannel)
	}

	private fun getNotificationManager(context: Context): NotificationManager {
		return context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
	}

	abstract class Channel {
		abstract val id: String
		abstract val nameRes: Int
		abstract val descriptionRes: Int
		abstract val importance: Int
		abstract val lockScreenVisibility: Int
	}

	object ToDoListChannel : Channel() {
		override val id: String = TO_DO_LIST_CHANNEL_ID
		@StringRes override val nameRes: Int = TO_DO_LIST_CHANNEL_NAME
		@StringRes override val descriptionRes: Int = R.string.channel_to_remind_about_to_do_list
		override val importance: Int = NotificationManager.IMPORTANCE_MAX
		override val lockScreenVisibility: Int = Notification.VISIBILITY_PUBLIC
	}
}