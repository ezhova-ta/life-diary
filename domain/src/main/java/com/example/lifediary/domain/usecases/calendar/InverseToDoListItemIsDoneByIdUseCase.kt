package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.repositories.ToDoListRepository
import javax.inject.Inject

class InverseToDoListItemIsDoneByIdUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	suspend operator fun invoke(id: Long) {
		toDoListRepository.inverseListItemIsDone(id)
	}
}