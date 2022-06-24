package com.example.lifediary.domain.usecases.shopping_list

import com.example.lifediary.data.repositories.ShoppingListRepository
import javax.inject.Inject

class ClearShoppingListUseCase @Inject constructor(
	private val shoppingListRepository: ShoppingListRepository
) {
	suspend operator fun invoke() {
		shoppingListRepository.clearShoppingList()
	}
}