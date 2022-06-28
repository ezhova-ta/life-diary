package com.example.lifediary.presentation.models.dropdowns

object DayNumberDropDownItem {
	val allElements: List<Int> by lazy { (1..31).toList() }

	fun getFromPosition(position: Int): Int {
		return allElements[position]
	}

	fun getPosition(element: Int): Int {
		return allElements.indexOf(element)
	}
}