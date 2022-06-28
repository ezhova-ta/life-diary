package com.example.lifediary.presentation.models.dropdowns

import android.content.Context
import com.example.lifediary.R
import com.example.lifediary.presentation.models.Text

enum class ToDoListSortMethodDropDownItem(val id: Int, val text: Text) : SortMethodDropDownItem {
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