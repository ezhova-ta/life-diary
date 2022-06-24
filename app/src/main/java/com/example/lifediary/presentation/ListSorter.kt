package com.example.lifediary.presentation

interface ListSorter<T> {
	fun sort(list: List<T>): List<T>
}