package com.example.lifediary.presentation.models.dropdowns

import android.content.Context
import com.example.lifediary.R
import com.example.lifediary.presentation.models.Text

enum class MainNoteListSortMethodDropDownItem(val id: Int, val text: Text) : SortMethodDropDownItem {
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