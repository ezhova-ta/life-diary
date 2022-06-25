package com.example.lifediary.domain.utils.sorters

interface ListSorter<T> {
	fun sort(list: List<T>): List<T>
}