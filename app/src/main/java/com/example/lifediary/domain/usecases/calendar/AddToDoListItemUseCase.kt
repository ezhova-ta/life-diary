package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.data.domain.ToDoListItem
import com.example.lifediary.data.repositories.ToDoListRepository
import javax.inject.Inject

class AddToDoListItemUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	suspend operator fun invoke(item: ToDoListItem) {
		toDoListRepository.addToDoListItem(item)
	}
}