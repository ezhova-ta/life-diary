package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.repositories.ToDoListRepository
import javax.inject.Inject

class AddToDoListItemUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	suspend operator fun invoke(item: ToDoListItem) {
		toDoListRepository.addToDoListItem(item)
	}
}