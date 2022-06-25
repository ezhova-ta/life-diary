package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.models.ShoppingListItemEntity

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list ORDER BY created_at DESC")
    fun getAll(): LiveData<List<ShoppingListItemEntity>>

    @Query("SELECT * FROM shopping_list WHERE id = :id")
    suspend fun get(id: Long): ShoppingListItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingListItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ShoppingListItemEntity)

    @Query("DELETE FROM shopping_list WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAll()
}