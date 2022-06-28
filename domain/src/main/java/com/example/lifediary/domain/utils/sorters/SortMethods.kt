package com.example.lifediary.domain.utils.sorters

/**
 * The class implements this interface to indicate that it enumerates sort methods for some list
 */
sealed interface SortMethod

enum class ShoppingListSortMethod(val id: Int) : SortMethod {
	BY_CREATION_DATE(1),
	ALPHABETICALLY(2),
	IMPORTANT_FIRST(3),
	CROSSED_OUT_LAST(4)
}

enum class ToDoListSortMethod(val id: Int) : SortMethod {
	BY_CREATION_DATE(1),
	ALPHABETICALLY(2),
	COMPLETED_LAST(3);
}

enum class MainNoteListSortMethod(val id: Int) : SortMethod {
	NEWEST_FIRST(1),
	OLDEST_FIRST(2)
}