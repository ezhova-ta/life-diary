package com.example.lifediary.domain.utils.sorters

import com.example.lifediary.domain.models.MainNote

sealed interface MainNoteListSorter : ListSorter<MainNote> {
	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): MainNoteListSorter {
				return when(sortMethodId) {
					MainNoteListSortMethod.OLDEST_FIRST.id -> MainNoteListSorterOldestFirst
					else -> MainNoteListSorterNewestFirst
				}
			}
		}
	}
}

object MainNoteListSorterNewestFirst : MainNoteListSorter {
	override fun sort(list: List<MainNote>): List<MainNote> {
		return list.sortedByDescending { it.createdAt }
	}
}

object MainNoteListSorterOldestFirst : MainNoteListSorter {
	override fun sort(list: List<MainNote>): List<MainNote> {
		return list.sortedBy { it.createdAt }
	}
}