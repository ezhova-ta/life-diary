package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.datasources.ToDoListLocalDataSource
import com.example.lifediary.data.db.entities.ToDoListItemEntity
import com.example.lifediary.data.repositories.mappers.ToDoListItemEntityMapper.toDomain
import com.example.lifediary.data.repositories.mappers.ToDoListItemEntityMapper.toEntity
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.repositories.ToDoListRepository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoListRepositoryImpl @Inject constructor(
	private val localDataSource: ToDoListLocalDataSource
) : ToDoListRepository {
	override fun getToDoList(day: Day): LiveData<List<ToDoListItem>> {
		return localDataSource.getToDoList(day.dayNumber, day.monthNumber, day.year).toDomain()
	}

	override fun getAllToDoLists(): LiveData<List<ToDoListItem>> {
		return localDataSource.getAllToDoLists().toDomain()
	}

	private fun LiveData<List<ToDoListItemEntity>>.toDomain(): LiveData<List<ToDoListItem>> {
		return map { entityList -> entityList.toDomain() }
	}

	private fun List<ToDoListItemEntity>.toDomain(): List<ToDoListItem> {
		return map { entity -> entity.toDomain() }
	}

	override fun getToDoListSortMethodId(): LiveData<Int?> {
		return localDataSource.getToDoListSortMethodId()
	}

	override suspend fun getToDoListItem(id: Long) : ToDoListItem? {
		return localDataSource.getToDoListItem(id)?.toDomain()
	}

	override suspend fun addToDoListItem(item: ToDoListItem) {
		localDataSource.addToDoListItem(item.toEntity())
	}

	override suspend fun clearToDoList(day: Day) {
		localDataSource.disableNotificationsFor(day.dayNumber, day.monthNumber, day.year)
		localDataSource.clearToDoList(day.dayNumber, day.monthNumber, day.year)
	}

	override suspend fun inverseListItemIsDone(id: Long) {
		localDataSource.inverseListItemIsDone(id)
	}

	override suspend fun enableListItemNotification(id: Long, time: Calendar) {
		localDataSource.enableListItemNotification(id, time)
	}

	override suspend fun disableListItemNotification(id: Long) {
		localDataSource.disableListItemNotification(id)
	}

	override suspend fun deleteToDoListItem(id: Long) {
		localDataSource.deleteToDoListItem(id)
	}

	override suspend fun clearToDoLists() {
		localDataSource.clearToDoLists()
	}

	override suspend fun saveToDoListSortMethodId(id: Int) {
		localDataSource.setToDoListSortMethodId(id)
	}
}