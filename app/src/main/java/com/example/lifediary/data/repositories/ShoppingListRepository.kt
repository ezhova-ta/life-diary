package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import com.example.lifediary.data.datasources.ShoppingListLocalDataSource
import com.example.lifediary.data.domain.ShoppingListItem
import javax.inject.Inject

class ShoppingListRepository @Inject constructor(
    private val localDataSource: ShoppingListLocalDataSource
) {
    fun getShoppingList(): LiveData<List<ShoppingListItem>> {
        return localDataSource.getShoppingList()
    }

    suspend fun saveShoppingListItem(item: ShoppingListItem) {
        localDataSource.saveShoppingListItem(item)
    }

    suspend fun clearShoppingList() {
        localDataSource.clearShoppingList()
    }

    suspend fun inverseShoppingListItemPriority(id: Long) {
        localDataSource.inverseShoppingListItemPriority(id)
    }

    suspend fun inverseListItemCrossedOut(id: Long) {
        localDataSource.inverseListItemCrossedOut(id)
    }

    suspend fun deleteShoppingListItem(id: Long) {
        localDataSource.deleteShoppingListItem(id)
    }
}