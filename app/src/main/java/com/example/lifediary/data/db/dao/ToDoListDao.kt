package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.ToDoListItemEntity

@Dao
interface ToDoListDao {
    @Query("SELECT * FROM to_do_list ORDER BY created_at DESC")
    fun getAll(): LiveData<List<ToDoListItemEntity>>

    @Query("SELECT * FROM to_do_list WHERE id = :id")
    suspend fun get(id: Long): ToDoListItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoListItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ToDoListItemEntity)

    @Delete
    suspend fun delete(item: ToDoListItemEntity)

    @Query("DELETE FROM to_do_list WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM to_do_list")
    suspend fun deleteAll()
}