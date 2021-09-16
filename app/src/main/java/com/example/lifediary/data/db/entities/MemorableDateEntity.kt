package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifediary.data.domain.MemorableDate
import com.example.lifediary.utils.Day

@Entity(tableName = "memorable_date")
data class MemorableDateEntity(
    @PrimaryKey
    val id: Long?,
    val name: String,
    @ColumnInfo(name = "day")
    val dayNumber: Int,
    @ColumnInfo(name = "month")
    val monthNumber: Int,
    val year: Int
) : DbEntity<MemorableDate>() {
    companion object {
        fun fromDomain(date: MemorableDate): MemorableDateEntity {
            return MemorableDateEntity(
                id = date.id,
                name = date.name,
                dayNumber = date.day.dayNumber,
                monthNumber = date.day.monthNumber,
                year = date.day.year
            )
        }
    }

    override fun toDomain(): MemorableDate {
        return MemorableDate(
            id = id,
            name = name,
            day = Day(dayNumber, monthNumber, year)
        )
    }
}
