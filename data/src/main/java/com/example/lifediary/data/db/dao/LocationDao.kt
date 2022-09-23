package com.example.lifediary.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lifediary.data.db.models.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM location LIMIT 1")
    suspend fun get(): LocationEntity?

    @Query("SELECT * FROM location LIMIT 1")
    fun getFlow(): Flow<LocationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Query("DELETE FROM location")
    suspend fun delete()
}