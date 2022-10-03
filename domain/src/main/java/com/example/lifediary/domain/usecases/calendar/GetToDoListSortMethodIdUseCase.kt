package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.domain.repositories.ToDoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetToDoListSortMethodIdUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	operator fun invoke(): Flow<Int?> {
		return toDoListRepository.getToDoListSortMethodIdFlow()
	}
}