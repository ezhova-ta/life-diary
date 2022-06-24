package com.example.lifediary.domain.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.ShoppingListItem

interface ShoppingListRepository {
	fun getShoppingList(): LiveData<List<ShoppingListItem>>
	fun getShoppingListSortMethodId(): LiveData<Int?>
	suspend fun addShoppingListItem(item: ShoppingListItem)
	suspend fun clearShoppingList()
	suspend fun inverseShoppingListItemPriority(id: Long)
	suspend fun inverseShoppingListItemCrossedOut(id: Long)
	suspend fun deleteShoppingListItem(id: Long)
	suspend fun saveShoppingListSortMethodId(id: Int)
}