package com.example.lifediary.domain.utils.searchers

interface ListItemSearcher<T> {
	fun search(list: List<T>, searchQuery: String): List<T>
}