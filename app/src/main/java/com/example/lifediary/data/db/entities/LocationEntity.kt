package com.example.lifediary.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "location", indices = [Index(value = ["name"], unique = true)])
data class LocationEntity(
    @PrimaryKey
    val id: Long?,
    val name: String,
    val lat: Double,
    val lon: Double
)
