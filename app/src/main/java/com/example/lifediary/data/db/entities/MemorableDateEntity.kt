package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.lifediary.domain.models.MemorableDate

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
) : DbEntity<MemorableDate>() {
    companion object {
        fun fromDomain(date: MemorableDate): MemorableDateEntity {
            return MemorableDateEntity(
                id = date.id,
                name = date.name,
                dayNumber = date.dayNumber,
                monthNumber = date.monthNumber,
                year = date.year
            )
        }
    }

    override fun toDomain(): MemorableDate {
        return MemorableDate(
            id = id,
            name = name,
            dayNumber = dayNumber,
            monthNumber = monthNumber,
            year = year
        )
    }
}
