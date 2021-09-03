package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.ToDoListLocalDataSource
import com.example.lifediary.data.domain.ToDoListItem
import javax.inject.Inject

class ToDoListRepository @Inject constructor(
	private val localDataSource: ToDoListLocalDataSource
) {
	fun getToDoList(): LiveData<List<ToDoListItem>> {
		return localDataSource.getToDoList()
	}

	suspend fun saveToDoListItem(item: ToDoListItem) {
		localDataSource.saveToDoListItem(item)
	}

	suspend fun clearToDoList() {
		localDataSource.clearToDoList()
	}

	suspend fun inverseListItemIsDone(id: Long) {
		localDataSource.inverseListItemIsDone(id)
	}

	suspend fun deleteToDoListItem(id: Long) {
		localDataSource.deleteToDoListItem(id)
	}
}