package com.example.lifediary.domain.usecases.shopping_list

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.repositories.ShoppingListRepository
import javax.inject.Inject

class GetShoppingListSortMethodIdUseCase @Inject constructor(
	private val shoppingListRepository: ShoppingListRepository
) {
	operator fun invoke(): LiveData<Int?> {
		return shoppingListRepository.getShoppingListSortMethodId()
	}
}