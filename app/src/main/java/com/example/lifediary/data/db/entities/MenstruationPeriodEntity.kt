package com.example.lifediary.data.db.entities

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.MenstruationPeriod
import java.util.*

@Entity(
    tableName = "menstruation_dates",
    indices = [Index(value = ["start_date", "end_date"], unique = true)]
)
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
