package com.example.lifediary.data.db.dao

import androidx.room.*
import com.example.lifediary.data.db.models.ToDoListItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListDao {
    @Query("SELECT * FROM to_do_list WHERE day = :dayNumber AND month = :monthNumber AND year = :year ORDER BY created_at ASC")
    fun getFlowAll(dayNumber: Int, monthNumber: Int, year: Int): Flow<List<ToDoListItemEntity>>

    @Query("SELECT * FROM to_do_list")
    fun getFlowAll(): Flow<List<ToDoListItemEntity>>

    @Query("SELECT * FROM to_do_list WHERE id = :id")
    suspend fun get(id: Long): ToDoListItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoListItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ToDoListItemEntity)

    @Query("DELETE FROM to_do_list WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM to_do_list WHERE day = :dayNumber AND month = :monthNumber AND year = :year")
    suspend fun deleteAll(dayNumber: Int, monthNumber: Int, year: Int)

    @Query("DELETE FROM to_do_list")
    suspend fun deleteAll()

    @Query("UPDATE to_do_list SET notification_enabled=0 WHERE day = :dayNumber AND month = :monthNumber AND year = :year")
    suspend fun disableNotificationsFor(dayNumber: Int, monthNumber: Int, year: Int)
}