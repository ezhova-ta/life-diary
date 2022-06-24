package com.example.lifediary.domain.usecases.calendar

import com.example.lifediary.data.repositories.ToDoListRepository
import java.util.*
import javax.inject.Inject

class EnableListItemNotificationByIdUseCase @Inject constructor(
	private val toDoListRepository: ToDoListRepository
) {
	suspend operator fun invoke(id: Long, time: Calendar) {
		toDoListRepository.enableListItemNotification(id, time)
	}
}