package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.PostAddressEntity

@Dao
interface PostAddressDao {
    @Query("SELECT * FROM post_address ORDER BY name ASC")
    fun getAll(): LiveData<List<PostAddressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: PostAddressEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(address: PostAddressEntity)

    @Query("DELETE FROM post_address WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM post_address")
    suspend fun deleteAll()
}