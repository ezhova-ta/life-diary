package com.example.lifediary.data.datasources

import com.example.lifediary.data.CommonDataStoreManager
import com.example.lifediary.data.db.dao.ToDoListDao
import com.example.lifediary.data.db.models.ToDoListItemEntity
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoListLocalDataSource @Inject constructor(
	private val dao: ToDoListDao,
	private val commonDataStoreManager: CommonDataStoreManager
) {
	fun getToDoList(dayNumber: Int, monthNumber: Int, year: Int): Flow<List<ToDoListItemEntity>> {
		return dao.getAll(dayNumber, monthNumber, year)
	}

	fun getAllToDoLists(): Flow<List<ToDoListItemEntity>> {
		return dao.getAll()
	}

	fun getToDoListSortMethodId(): Flow<Int?> {
		return commonDataStoreManager.toDoListSortMethodId
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