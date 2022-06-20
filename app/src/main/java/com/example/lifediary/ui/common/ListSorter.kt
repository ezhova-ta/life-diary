package com.example.lifediary.ui.common

interface ListSorter<T> {
	fun sort(list: List<T>): List<T>
}