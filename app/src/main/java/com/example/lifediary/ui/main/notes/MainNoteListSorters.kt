package com.example.lifediary.ui.main.notes

import com.example.lifediary.data.domain.MainNote
import com.example.lifediary.data.domain.MainNoteListSortMethodDropDownItem.OLDEST_FIRST
import com.example.lifediary.ui.common.ListSorter

sealed interface MainNoteListSorter : ListSorter<MainNote> {
	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): MainNoteListSorter {
				return when(sortMethodId) {
					OLDEST_FIRST.id -> MainNoteListSorterOldestFirst
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