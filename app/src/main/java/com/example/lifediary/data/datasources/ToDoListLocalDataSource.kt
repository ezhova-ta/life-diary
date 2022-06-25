package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lifediary.data.CommonDataStoreManager
import com.example.lifediary.data.db.dao.ToDoListDao
import com.example.lifediary.data.db.models.ToDoListItemEntity
import java.util.*
import javax.inject.Inject

class ToDoListLocalDataSource @Inject constructor(
	private val dao: ToDoListDao,
	private val commonDataStoreManager: CommonDataStoreManager
) {
	fun getToDoList(dayNumber: Int, monthNumber: Int, year: Int): LiveData<List<ToDoListItemEntity>> {
		return dao.getAll(dayNumber, monthNumber, year)
	}

	fun getAllToDoLists(): LiveData<List<ToDoListItemEntity>> {
		return dao.getAll()
	}

	fun getToDoListSortMethodId(): LiveData<Int?> {
		return commonDataStoreManager.toDoListSortMethodId.asLiveData()
	}

	suspend fun getToDoListItem(id: Long) : ToDoListItemEntity? {
		return dao.get(id)
	}

	suspend fun addToDoListItem(item: ToDoListItemEntity) {
		dao.insert(item)
	}

	suspend fun clearToDoList(dayNumber: Int, monthNumber: Int, year: Int) {
		dao.deleteAll(dayNumber, monthNumber, year)
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

	suspend fun disableNotificationsFor(dayNumber: Int, monthNumber: Int, year: Int) {
		dao.disableNotificationsFor(dayNumber, monthNumber, year)
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