package com.example.lifediary.data.domain

import android.content.Context
import com.example.lifediary.R

enum class MonthDropDownItem(val number: Int, val text: Text) {
	JANUARY(1, Text.TextResource(R.string.january)),
	FEBRUARY(2, Text.TextResource(R.string.february)),
	MARCH(3, Text.TextResource(R.string.march)),
	APRIL(4, Text.TextResource(R.string.april)),
	MAY(5, Text.TextResource(R.string.may)),
	JUNE(6, Text.TextResource(R.string.june)),
	JULY(7, Text.TextResource(R.string.july)),
	AUGUST(8, Text.TextResource(R.string.august)),
	SEPTEMBER(9, Text.TextResource(R.string.september)),
	OCTOBER(10, Text.TextResource(R.string.october)),
	NOVEMBER(11, Text.TextResource(R.string.november)),
	DECEMBER(12, Text.TextResource(R.string.december));

	companion object {
		private val allElements: List<MonthDropDownItem> by lazy {
			listOf(
				JANUARY,
				FEBRUARY,
				MARCH,
				APRIL,
				MAY,
				JUNE,
				JULY,
				AUGUST,
				SEPTEMBER,
				OCTOBER,
				NOVEMBER,
				DECEMBER
			)
		}

		fun getAllStrings(context: Context): List<String> {
			return allElements.map { it.text.getText(context) }
		}

		fun getFromPosition(position: Int): MonthDropDownItem {
			return allElements[position]
		}

		fun getPositionFromNumber(number: Int): Int {
			val element = allElements.find { it.number == number } ?: return 0
			return allElements.indexOf(element)
		}
	}
}

object DayNumberDropDownItem {
	val allElements: List<Int> by lazy { (1..31).toList() }

	fun getFromPosition(position: Int): Int {
		return allElements[position]
	}

	fun getPosition(element: Int): Int {
		return allElements.indexOf(element)
	}
}

enum class ShoppingListSortMethodDropDownItem(val id: Int, val text: Text) {
	BY_CREATION_DATE(1, Text.TextResource(R.string.by_creation_date)),
	ALPHABETICALLY(2, Text.TextResource(R.string.alphabetically)),
	IMPORTANT_FIRST(3, Text.TextResource(R.string.important_first)),
	CROSSED_OUT_LAST(4, Text.TextResource(R.string.bought_last));

	companion object {
		private val allElements: List<ShoppingListSortMethodDropDownItem> by lazy {
			listOf(BY_CREATION_DATE, ALPHABETICALLY, IMPORTANT_FIRST, CROSSED_OUT_LAST)
		}

		fun getAllStrings(context: Context): List<String> {
			return allElements.map { it.text.getText(context) }
		}

		fun getFromPosition(position: Int): ShoppingListSortMethodDropDownItem {
			return allElements[position]
		}

		fun getPositionFromId(id: Int): Int {
			val element = allElements.find { it.id == id } ?: return 0
			return allElements.indexOf(element)
		}
	}
}

enum class ToDoListSortMethodDropDownItem(val id: Int, val text: Text) {
	BY_CREATION_DATE(1, Text.TextResource(R.string.by_creation_date)),
	ALPHABETICALLY(2, Text.TextResource(R.string.alphabetically)),
	COMPLETED_LAST(3, Text.TextResource(R.string.completed_last));

	companion object {
		private val allElements: List<ToDoListSortMethodDropDownItem> by lazy {
			listOf(BY_CREATION_DATE, ALPHABETICALLY, COMPLETED_LAST)
		}

		fun getAllStrings(context: Context): List<String> {
			return allElements.map { it.text.getText(context) }
		}

		fun getFromPosition(position: Int): ToDoListSortMethodDropDownItem {
			return allElements[position]
		}

		fun getPositionFromId(id: Int): Int {
			val element = allElements.find { it.id == id } ?: return 0
			return allElements.indexOf(element)
		}
	}
}

enum class MainNoteListSortMethodDropDownItem(val id: Int, val text: Text) {
	NEWEST_FIRST(1, Text.TextResource(R.string.newest_first)),
	OLDEST_FIRST(2, Text.TextResource(R.string.oldest_first));

	companion object {
		private val allElements: List<MainNoteListSortMethodDropDownItem> by lazy {
			listOf(NEWEST_FIRST, OLDEST_FIRST)
		}

		fun getAllStrings(context: Context): List<String> {
			return allElements.map { it.text.getText(context) }
		}

		fun getFromPosition(position: Int): MainNoteListSortMethodDropDownItem {
			return allElements[position]
		}

		fun getPositionFromId(id: Int): Int {
			val element = allElements.find { it.id == id } ?: return 0
			return allElements.indexOf(element)
		}
	}
}