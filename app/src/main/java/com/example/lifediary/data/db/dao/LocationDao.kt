package com.example.lifediary.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lifediary.data.db.entities.LocationEntity

@Dao
interface LocationDao {
    @Query("SELECT * FROM location LIMIT 1")
    fun get(): LiveData<LocationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(location: LocationEntity)

    @Query("DELETE FROM location")
    suspend fun delete()
}