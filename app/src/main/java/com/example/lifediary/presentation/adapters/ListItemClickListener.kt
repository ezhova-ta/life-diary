package com.example.lifediary.presentation.adapters

class ListItemClickListener<T>(val clickListener: (T) -> Unit) {
	fun onClick(item: T) = clickListener(item)
}