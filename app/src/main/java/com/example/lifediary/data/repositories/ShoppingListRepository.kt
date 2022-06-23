package com.example.lifediary.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lifediary.data.datasources.ShoppingListLocalDataSource
import com.example.lifediary.data.domain.ShoppingListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepository @Inject constructor(
    private val localDataSource: ShoppingListLocalDataSource
) {
    fun getShoppingList(): LiveData<List<ShoppingListItem>> {
        return localDataSource.getShoppingList()
    }

    fun getShoppingListSortMethodId(): LiveData<Int?> {
        return localDataSource.getShoppingListSortMethodId()
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

    suspend fun inverseShoppingListItemCrossedOut(id: Long) {
        localDataSource.inverseListItemCrossedOut(id)
    }

    suspend fun deleteShoppingListItem(id: Long) {
        localDataSource.deleteShoppingListItem(id)
    }

    suspend fun saveShoppingListSortMethodId(id: Int) {
        localDataSource.setShoppingListSortMethodId(id)
    }
}