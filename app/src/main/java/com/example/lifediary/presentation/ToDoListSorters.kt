package com.example.lifediary.presentation

import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.presentation.ToDoListSortMethodDropDownItem.ALPHABETICALLY
import com.example.lifediary.presentation.ToDoListSortMethodDropDownItem.COMPLETED_LAST

sealed interface ToDoListSorter : ListSorter<ToDoListItem> {
	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): ToDoListSorter {
				return when(sortMethodId) {
					ALPHABETICALLY.id -> ToDoListSorterAlphabetically
					COMPLETED_LAST.id -> ToDoListSorterCompletedLast
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