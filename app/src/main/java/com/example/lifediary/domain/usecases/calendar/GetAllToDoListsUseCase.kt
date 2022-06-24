package com.example.lifediary.domain.usecases.calendar

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.ToDoListItem
import com.example.lifediary.domain.repositories.ToDoListRepository
import javax.inject.Inject

class GetAllToDoListsUseCase @Inject constructor(
	private val doToDoListRepository: ToDoListRepository
) {
	operator fun invoke(): LiveData<List<ToDoListItem>> {
		return doToDoListRepository.getAllToDoLists()
	}
}