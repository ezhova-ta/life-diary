package com.example.lifediary.domain.usecases.shopping_list

import com.example.lifediary.domain.repositories.ShoppingListRepository
import javax.inject.Inject

class InverseShoppingListItemPriorityByIdUseCase @Inject constructor(
	private val shoppingListRepository: ShoppingListRepository
) {
	suspend operator fun invoke(id: Long) {
		shoppingListRepository.inverseShoppingListItemPriority(id)
	}
}