package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.MenstruationPeriod
import java.util.*

@Entity(tableName = "menstruation_dates")
@TypeConverters(CalendarConverter::class)
data class MenstruationPeriodEntity(
    @PrimaryKey
    val id: Long?,
    @ColumnInfo(name = "start_date")
    var startDate: Calendar,
    @ColumnInfo(name = "end_date")
    var endDate: Calendar
) : DbEntity<MenstruationPeriod>() {
    companion object {
        fun fromDomain(menstruationPeriod: MenstruationPeriod): MenstruationPeriodEntity {
            return MenstruationPeriodEntity(
                id = menstruationPeriod.id,
                startDate = menstruationPeriod.startDate,
                endDate = menstruationPeriod.endDate
            )
        }
    }

    override fun toDomain(): MenstruationPeriod {
        return MenstruationPeriod(
            id = id,
            startDate = startDate,
            endDate = endDate
        )
    }
}
