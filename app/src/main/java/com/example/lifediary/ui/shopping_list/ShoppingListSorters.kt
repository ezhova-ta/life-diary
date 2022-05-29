package com.example.lifediary.ui.shopping_list

import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.data.domain.ShoppingListSortMethodDropDownItem

sealed interface ShoppingListSorter {
	fun sort(list: List<ShoppingListItem>): List<ShoppingListItem>

	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): ShoppingListSorter {
				return when(sortMethodId) {
					ShoppingListSortMethodDropDownItem.ALPHABETICALLY.id -> ShoppingListSorterAlphabetically
					ShoppingListSortMethodDropDownItem.IMPORTANT_FIRST.id -> ShoppingListSorterImportantFirst
					ShoppingListSortMethodDropDownItem.CROSSED_OUT_LAST.id -> ShoppingListSorterCrossedOutLast
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