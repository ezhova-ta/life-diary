package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.MenstruationDates
import java.util.*

@Entity(tableName = "menstruation_dates")
@TypeConverters(CalendarConverter::class)
data class MenstruationDatesEntity(
    @PrimaryKey
    val id: Long?,
    @ColumnInfo(name = "start_date")
    var startDate: Calendar,
    @ColumnInfo(name = "end_date")
    var endDate: Calendar
) : DbEntity<MenstruationDates>() {
    companion object {
        fun fromDomain(menstruationDates: MenstruationDates): MenstruationDatesEntity {
            return MenstruationDatesEntity(
                id = menstruationDates.id,
                startDate = menstruationDates.startDate,
                endDate = menstruationDates.endDate
            )
        }
    }

    override fun toDomain(): MenstruationDates {
        return MenstruationDates(
            id = id,
            startDate = startDate,
            endDate = endDate
        )
    }
}
