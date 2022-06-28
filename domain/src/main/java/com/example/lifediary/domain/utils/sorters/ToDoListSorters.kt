package com.example.lifediary.domain.utils.sorters

import com.example.lifediary.domain.models.ToDoListItem

sealed interface ToDoListSorter : ListSorter<ToDoListItem> {
	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): ToDoListSorter {
				return when(sortMethodId) {
					ToDoListSortMethod.ALPHABETICALLY.id -> ToDoListSorterAlphabetically
					ToDoListSortMethod.COMPLETED_LAST.id -> ToDoListSorterCompletedLast
					else -> ToDoListSorterByCreationDate
				}
			}
		}
	}
}

object ToDoListSorterByCreationDate : ToDoListSorter {
	override fun sort(list: List<ToDoListItem>): List<ToDoListItem> {
		return list.sortedBy { it.createdAt }
	}
}

object ToDoListSorterAlphabetically : ToDoListSorter {
	override fun sort(list: List<ToDoListItem>): List<ToDoListItem> {
		return list.sortedBy { it.text }
	}
}

object ToDoListSorterCompletedLast : ToDoListSorter {
	override fun sort(list: List<ToDoListItem>): List<ToDoListItem> {
		return list.sortedBy { it.isDone }
	}
}