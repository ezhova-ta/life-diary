package com.example.lifediary.domain

enum class ShoppingListSortMethod(val id: Int) {
	BY_CREATION_DATE(1),
	ALPHABETICALLY(2),
	IMPORTANT_FIRST(3),
	CROSSED_OUT_LAST(4)
}

enum class ToDoListSortMethod(val id: Int) {
	BY_CREATION_DATE(1),
	ALPHABETICALLY(2),
	COMPLETED_LAST(3);
}

enum class MainNoteListSortMethod(val id: Int) {
	NEWEST_FIRST(1),
	OLDEST_FIRST(2)
}