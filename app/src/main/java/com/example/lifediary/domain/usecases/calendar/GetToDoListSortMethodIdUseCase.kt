package com.example.lifediary.domain.usecases.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.data.repositories.ToDoListRepository
import javax.inject.Inject

class GetToDoListSortMethodIdUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	operator fun invoke(): LiveData<Int?> {
		return toDoListRepository.getToDoListSortMethodId()
	}
}