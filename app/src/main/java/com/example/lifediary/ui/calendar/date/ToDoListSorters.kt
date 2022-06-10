package com.example.lifediary.ui.calendar.date

import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.data.domain.ToDoListSortMethodDropDownItem.ALPHABETICALLY
import com.example.lifediary.data.domain.ToDoListSortMethodDropDownItem.COMPLETED_LAST

sealed interface ToDoListSorter {
	fun sort(list: List<ToDoListItem>): List<ToDoListItem>

	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): ToDoListSorter {
				return when(sortMethodId) {
					ALPHABETICALLY.id -> ToDoListSorterAlphabetically
					COMPLETED_LAST.id -> ToDoListSorterCompletedLast
					else ->ToDoListSorterByCreationDate
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