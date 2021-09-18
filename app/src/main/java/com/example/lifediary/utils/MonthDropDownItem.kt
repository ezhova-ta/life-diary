package com.example.lifediary.utils

import android.content.Context
import com.example.lifediary.R

enum class MonthDropDownItem(val number: Int, val text: Text) {
	JANUARY(1, Text.TextResource(R.string.january)),
	FEBRUARY(2, Text.TextResource(R.string.february)),
	MARCH(3, Text.TextResource(R.string.march)),
	APRIL(4, Text.TextResource(R.string.april)),
	MAY(5, Text.TextResource(R.string.may)),
	JINE(6, Text.TextResource(R.string.june)),
	JULY(7, Text.TextResource(R.string.july)),
	AUGUST(8, Text.TextResource(R.string.august)),
	SEPTEMBER(9, Text.TextResource(R.string.september)),
	OCTOBER(10, Text.TextResource(R.string.october)),
	NOVEMBER(11, Text.TextResource(R.string.november)),
	DECEMBER(12, Text.TextResource(R.string.december));

	companion object {
		fun getAll(): Array<MonthDropDownItem> {
			return arrayOf(
				JANUARY,
				FEBRUARY,
				MARCH,
				APRIL,
				MAY,
				JINE,
				JULY,
				AUGUST,
				SEPTEMBER,
				OCTOBER,
				NOVEMBER,
				DECEMBER
			)
		}

		fun getAllStrings(context: Context): List<String> {
			return getAll().map { it.text.getText(context) }
		}

		fun getFromPosition(position: Int): MonthDropDownItem? {
			return try {
				getAll()[position]
			} catch(e: IndexOutOfBoundsException) {
				null
			}
		}
	}
}