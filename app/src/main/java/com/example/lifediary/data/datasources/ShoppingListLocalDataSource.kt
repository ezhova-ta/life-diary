package com.example.lifediary.data.datasources

import androidx.lifecycle.LiveData
import com.example.lifediary.data.db.dao.ShoppingListDao
import com.example.lifediary.data.db.entities.ShoppingListItemEntity
import com.example.lifediary.data.domain.ShoppingListItem
import com.example.lifediary.utils.toDomain
import javax.inject.Inject

class ShoppingListLocalDataSource @Inject constructor(private val dao: ShoppingListDao) {
    fun getShoppingList(): LiveData<List<ShoppingListItem>> {
        return dao.getAll().toDomain()
    }

    suspend fun saveShoppingListItem(item: ShoppingListItem) {
        dao.insert(ShoppingListItemEntity.fromDomain(item))
    }

    suspend fun clearShoppingList() {
        dao.deleteAll()
    }

    suspend fun inverseShoppingListItemPriority(id: Long) {
        val item = dao.get(id) ?: return
        val isHighPriority = item.isHighPriority
        item.isHighPriority = !isHighPriority
        dao.update(item)
    }

    suspend fun inverseListItemCrossedOut(id: Long) {
        val item = dao.get(id) ?: return
        val isCrossedOut = item.isCrossedOut
        item.isCrossedOut = !isCrossedOut
        dao.update(item)
    }

    suspend fun deleteShoppingListItem(id: Long) {
        dao.delete(id)
    }
}