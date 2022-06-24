package com.example.lifediary.presentation.ui.shopping_list

import com.example.lifediary.domain.models.ShoppingListItem
import com.example.lifediary.presentation.ShoppingListSortMethodDropDownItem.*
import com.example.lifediary.presentation.ListSorter

sealed interface ShoppingListSorter : ListSorter<ShoppingListItem> {
	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): ShoppingListSorter {
				return when(sortMethodId) {
					ALPHABETICALLY.id -> ShoppingListSorterAlphabetically
					IMPORTANT_FIRST.id -> ShoppingListSorterImportantFirst
					CROSSED_OUT_LAST.id -> ShoppingListSorterCrossedOutLast
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