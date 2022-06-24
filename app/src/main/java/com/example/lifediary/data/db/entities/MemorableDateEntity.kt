package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "memorable_date",
    indices = [Index(value = ["name", "day", "month", "year"], unique = true)]
)
data class MemorableDateEntity(
    @PrimaryKey
    val id: Long?,
    val name: String,
    @ColumnInfo(name = "day")
    val dayNumber: Int,
    @ColumnInfo(name = "month")
    val monthNumber: Int,
    val year: Int?
)
