package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lifediary.data.CommonDataStoreManager
import com.example.lifediary.data.db.dao.ToDoListDao
import com.example.lifediary.data.db.entities.ToDoListItemEntity
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.models.Day
import com.example.lifediary.presentation.utils.toDomain
import java.util.*
import javax.inject.Inject

class ToDoListLocalDataSource @Inject constructor(
	private val dao: ToDoListDao,
	private val commonDataStoreManager: CommonDataStoreManager
) {
	fun getToDoList(day: Day): LiveData<List<ToDoListItem>> {
		return dao.getAll(day.dayNumber, day.monthNumber, day.year).toDomain()
	}

	fun getAllToDoLists(): LiveData<List<ToDoListItem>> {
		return dao.getAll().toDomain()
	}

	fun getToDoListSortMethodId(): LiveData<Int?> {
		return commonDataStoreManager.toDoListSortMethodId.asLiveData()
	}

	suspend fun getToDoListItem(id: Long) : ToDoListItem? {
		return dao.get(id)?.toDomain()
	}

	suspend fun addToDoListItem(item: ToDoListItem) {
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

	suspend fun setToDoListSortMethodId(id: Int) {
		commonDataStoreManager.setToDoListSortMethodId(id)
	}
}