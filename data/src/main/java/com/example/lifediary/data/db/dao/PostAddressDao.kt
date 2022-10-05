package com.example.lifediary.data.db.dao

import androidx.room.*
import com.example.lifediary.data.db.models.PostAddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostAddressDao {
    @Query("SELECT * FROM post_address ORDER BY name ASC")
    fun getFlowAll(): Flow<List<PostAddressEntity>>

    @Query("SELECT * FROM post_address ORDER BY name ASC")
    suspend fun getAll(): List<PostAddressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: PostAddressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(addresses: List<PostAddressEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(address: PostAddressEntity)

    @Query("DELETE FROM post_address WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM post_address")
    suspend fun deleteAll()
}