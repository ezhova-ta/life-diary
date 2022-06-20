package com.example.lifediary.ui.common

interface ListItemSearcher<T> {
	fun search(list: List<T>, searchQuery: String): List<T>
}