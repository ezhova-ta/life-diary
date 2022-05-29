package com.example.lifediary.ui.shopping_list

import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.data.domain.SortMethodDropDownItem

sealed interface ShoppingListSorter {
	fun sort(list: List<ShoppingListItem>): List<ShoppingListItem>

	class Factory {
		companion object {
			fun getInstance(sortMethodId: Int?): ShoppingListSorter {
				return when(sortMethodId) {
					SortMethodDropDownItem.ALPHABETICALLY.id -> ShoppingListSorterAlphabetically
					SortMethodDropDownItem.IMPORTANT_FIRST.id -> ShoppingListSorterImportantFirst
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