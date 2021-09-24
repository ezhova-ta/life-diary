package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.ToDoListLocalDataSource
import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.utils.Day
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoListRepository @Inject constructor(
	private val localDataSource: ToDoListLocalDataSource
) {
	fun getToDoList(day: Day): LiveData<List<ToDoListItem>> {
		return localDataSource.getToDoList(day)
	}

	fun getAllToDoLists(): LiveData<List<ToDoListItem>> {
		return localDataSource.getAllToDoLists()
	}

	suspend fun getToDoListItem(id: Long) : ToDoListItem? {
		return localDataSource.getToDoListItem(id)
	}

	suspend fun saveToDoListItem(item: ToDoListItem) {
		localDataSource.saveToDoListItem(item)
	}

	suspend fun clearToDoList(day: Day) {
		localDataSource.clearToDoList(day)
	}

	suspend fun inverseListItemIsDone(id: Long) {
		localDataSource.inverseListItemIsDone(id)
	}

	suspend fun enableListItemNotification(id: Long) {
		localDataSource.enableListItemNotification(id)
	}

	suspend fun disableListItemNotification(id: Long) {
		localDataSource.disableListItemNotification(id)
	}

	suspend fun deleteToDoListItem(id: Long) {
		localDataSource.deleteToDoListItem(id)
	}

	suspend fun clearToDoLists() {
		localDataSource.clearToDoLists()
	}
}