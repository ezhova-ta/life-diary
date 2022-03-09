package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.ToDoListDao
import com.example.lifediary.data.db.entities.ToDoListItemEntity
import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.data.domain.Day
import com.example.lifediary.utils.toDomain
import java.util.*
import javax.inject.Inject

class ToDoListLocalDataSource @Inject constructor(private val dao: ToDoListDao) {
	fun getToDoList(day: Day): LiveData<List<ToDoListItem>> {
		return dao.getAll(day.dayNumber, day.monthNumber, day.year).toDomain()
	}

	fun getAllToDoLists(): LiveData<List<ToDoListItem>> {
		return dao.getAll().toDomain()
	}

	suspend fun getToDoListItem(id: Long) : ToDoListItem? {
		return dao.get(id)?.toDomain()
	}

	suspend fun saveToDoListItem(item: ToDoListItem) {
		dao.insert(ToDoListItemEntity.fromDomain(item))
	}

	suspend fun clearToDoList(day: Day) {
		dao.deleteAll(day.dayNumber, day.monthNumber, day.year)
	}

	suspend fun inverseListItemIsDone(id: Long) {
		val item = dao.get(id) ?: return
		val isDone = item.isDone
		item.isDone = !isDone
		dao.update(item)
	}

	suspend fun enableListItemNotification(id: Long, time: Calendar) {
		val item = dao.get(id) ?: return
		item.notificationEnabled = true
		item.notificationTime = time
		dao.update(item)
	}

	suspend fun disableListItemNotification(id: Long) {
		val item = dao.get(id) ?: return
		item.notificationEnabled = false
		dao.update(item)
	}

	suspend fun disableNotificationsFor(day: Day) {
		dao.disableNotificationsFor(day.dayNumber, day.monthNumber, day.year)
	}

	suspend fun deleteToDoListItem(id: Long) {
		dao.delete(id)
	}

	suspend fun clearToDoLists() {
		dao.deleteAll()
	}
}