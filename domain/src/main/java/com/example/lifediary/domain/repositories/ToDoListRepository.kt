package com.example.lifediary.domain.repositories

import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.ToDoListItem
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ToDoListRepository {
	fun getToDoListFlow(day: Day): Flow<List<ToDoListItem>>
	fun getFlowAllToDoLists(): Flow<List<ToDoListItem>>
	suspend fun getAllToDoLists(): List<ToDoListItem>
	fun getToDoListSortMethodIdFlow(): Flow<Int?>
	suspend fun getToDoListItem(id: Long) : ToDoListItem?
	suspend fun addToDoListItem(item: ToDoListItem)
	suspend fun clearToDoList(day: Day)
	suspend fun inverseListItemIsDone(id: Long)
	suspend fun enableListItemNotification(id: Long, time: Calendar)
	suspend fun disableListItemNotification(id: Long)
	suspend fun deleteToDoListItem(id: Long)
	suspend fun clearToDoLists()
	suspend fun saveToDoListSortMethodId(id: Int)
}