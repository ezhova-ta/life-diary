package com.example.lifediary.presentation

interface ListItemSearcher<T> {
	fun search(list: List<T>, searchQuery: String): List<T>
}