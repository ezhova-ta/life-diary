package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.Day
import com.example.lifediary.domain.models.ToDoListItem
import java.util.*

interface ToDoListRepository {
	fun getToDoList(day: Day): LiveData<List<ToDoListItem>>
	fun getAllToDoLists(): LiveData<List<ToDoListItem>>
	fun getToDoListSortMethodId(): LiveData<Int?>
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