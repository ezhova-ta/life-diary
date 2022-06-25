package com.example.lifediary.presentation

import com.example.lifediary.domain.MainNoteListSortMethod
import com.example.lifediary.domain.ShoppingListSortMethod
import com.example.lifediary.domain.ToDoListSortMethod

/**
 * Converts a UI drop down item to a domain sort method and vice versa
 *
 * @param T UI drop down item type
 * @param R domain sort method
 */
sealed interface DropDownItemSortMethodMapper<T, R> {
	fun T.toSortMethod(): R
	fun R.toDropDownItem(): T
}

object ShoppingListDropDownItemSortMethodMapper :
	DropDownItemSortMethodMapper<ShoppingListSortMethodDropDownItem, ShoppingListSortMethod> {

	override fun ShoppingListSortMethodDropDownItem.toSortMethod(): ShoppingListSortMethod {
		return when(this) {
			ShoppingListSortMethodDropDownItem.BY_CREATION_DATE -> ShoppingListSortMethod.BY_CREATION_DATE
			ShoppingListSortMethodDropDownItem.ALPHABETICALLY -> ShoppingListSortMethod.ALPHABETICALLY
			ShoppingListSortMethodDropDownItem.IMPORTANT_FIRST -> ShoppingListSortMethod.IMPORTANT_FIRST
			ShoppingListSortMethodDropDownItem.CROSSED_OUT_LAST -> ShoppingListSortMethod.CROSSED_OUT_LAST
		}
	}

	override fun ShoppingListSortMethod.toDropDownItem(): ShoppingListSortMethodDropDownItem {
		return when(this) {
			ShoppingListSortMethod.BY_CREATION_DATE -> ShoppingListSortMethodDropDownItem.BY_CREATION_DATE
			ShoppingListSortMethod.ALPHABETICALLY -> ShoppingListSortMethodDropDownItem.ALPHABETICALLY
			ShoppingListSortMethod.IMPORTANT_FIRST -> ShoppingListSortMethodDropDownItem.IMPORTANT_FIRST
			ShoppingListSortMethod.CROSSED_OUT_LAST -> ShoppingListSortMethodDropDownItem.CROSSED_OUT_LAST
		}
	}
}

object ToDoListDropDownItemSortMethodMapper :
	DropDownItemSortMethodMapper<ToDoListSortMethodDropDownItem, ToDoListSortMethod> {

	override fun ToDoListSortMethodDropDownItem.toSortMethod(): ToDoListSortMethod {
		return when(this) {
			ToDoListSortMethodDropDownItem.BY_CREATION_DATE -> ToDoListSortMethod.BY_CREATION_DATE
			ToDoListSortMethodDropDownItem.ALPHABETICALLY -> ToDoListSortMethod.ALPHABETICALLY
			ToDoListSortMethodDropDownItem.COMPLETED_LAST -> ToDoListSortMethod.COMPLETED_LAST
		}
	}

	override fun ToDoListSortMethod.toDropDownItem(): ToDoListSortMethodDropDownItem {
		return when(this) {
			ToDoListSortMethod.BY_CREATION_DATE -> ToDoListSortMethodDropDownItem.BY_CREATION_DATE
			ToDoListSortMethod.ALPHABETICALLY -> ToDoListSortMethodDropDownItem.ALPHABETICALLY
			ToDoListSortMethod.COMPLETED_LAST -> ToDoListSortMethodDropDownItem.COMPLETED_LAST
		}
	}

}

object MainNoteListDropDownItemSortMethodMapper :
	DropDownItemSortMethodMapper<MainNoteListSortMethodDropDownItem, MainNoteListSortMethod> {

	override fun MainNoteListSortMethodDropDownItem.toSortMethod(): MainNoteListSortMethod {
		return when(this) {
			MainNoteListSortMethodDropDownItem.NEWEST_FIRST -> MainNoteListSortMethod.NEWEST_FIRST
			MainNoteListSortMethodDropDownItem.OLDEST_FIRST -> MainNoteListSortMethod.OLDEST_FIRST
		}
	}

	override fun MainNoteListSortMethod.toDropDownItem(): MainNoteListSortMethodDropDownItem {
		return when(this) {
			MainNoteListSortMethod.NEWEST_FIRST -> MainNoteListSortMethodDropDownItem.NEWEST_FIRST
			MainNoteListSortMethod.OLDEST_FIRST -> MainNoteListSortMethodDropDownItem.OLDEST_FIRST
		}
	}
}