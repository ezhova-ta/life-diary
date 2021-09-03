package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.ToDoListDao
import com.example.lifediary.data.db.entities.ToDoListItemEntity
import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.utils.toDomain
import javax.inject.Inject

class ToDoListLocalDataSource @Inject constructor(private val dao: ToDoListDao) {
	fun getToDoList(): LiveData<List<ToDoListItem>> {
		return dao.getAll().toDomain()
	}

	suspend fun saveToDoListItem(item: ToDoListItem) {
		dao.insert(ToDoListItemEntity.fromDomain(item))
	}

	suspend fun clearToDoList() {
		dao.deleteAll()
	}

	suspend fun inverseListItemIsDone(id: Long) {
		val item = dao.get(id) ?: return
		val isDone = item.isDone
		item.isDone = !isDone
		dao.update(item)
	}

	suspend fun deleteToDoListItem(id: Long) {
		dao.delete(id)
	}
}