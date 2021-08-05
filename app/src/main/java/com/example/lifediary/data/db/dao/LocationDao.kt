package com.example.lifediary.data.db.dao

import androidx.room.*
import com.example.lifediary.data.db.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Query("SELECT * FROM location LIMIT 1")
    fun getLocationFlow(): Flow<LocationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(location: LocationEntity)

    @Query("DELETE FROM shopping_list")
    suspend fun delete()
}