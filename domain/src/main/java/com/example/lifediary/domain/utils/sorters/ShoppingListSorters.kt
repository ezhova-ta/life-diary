package com.example.lifediary.domain.utils.sorters

import com.example.lifediary.domain.models.ShoppingListItem

sealed interface ShoppingListSorter : ListSorter<ShoppingListItem> {
	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): ShoppingListSorter {
				return when(sortMethodId) {
					ShoppingListSortMethod.ALPHABETICALLY.id -> ShoppingListSorterAlphabetically
					ShoppingListSortMethod.IMPORTANT_FIRST.id -> ShoppingListSorterImportantFirst
					ShoppingListSortMethod.CROSSED_OUT_LAST.id -> ShoppingListSorterCrossedOutLast
					else -> ShoppingListSorterByCreationDate
				}
			}
		}
	}
}

object ShoppingListSorterByCreationDate : ShoppingListSorter {
	override fun sort(list: List<ShoppingListItem>): List<ShoppingListItem> {
		return list.sortedByDescending { it.createdAt }
	}
}

object ShoppingListSorterAlphabetically : ShoppingListSorter {
	override fun sort(list: List<ShoppingListItem>): List<ShoppingListItem> {
		return list.sortedBy { it.text }
	}
}

object ShoppingListSorterImportantFirst : ShoppingListSorter {
	override fun sort(list: List<ShoppingListItem>): List<ShoppingListItem> {
		return list.sortedByDescending { it.isHighPriority }
	}
}

object ShoppingListSorterCrossedOutLast : ShoppingListSorter {
	override fun sort(list: List<ShoppingListItem>): List<ShoppingListItem> {
		return list.sortedBy { it.isCrossedOut }
	}
}