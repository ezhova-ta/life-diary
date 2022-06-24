package com.example.lifediary.data.db.entities

import androidx.room.*
import com.example.lifediary.data.db.converters.CalendarConverter
import java.util.*

@Entity(
    tableName = "post_address",
    indices = [Index(
        value = ["name", "street", "building_number", "apartment_number", "city", "postcode", "edge_region"],
        unique = true
    )]
)
@TypeConverters(CalendarConverter::class)
data class PostAddressEntity(
    @PrimaryKey
    val id: Long?,
    val name: String,
    val street: String,
    @ColumnInfo(name = "building_number")
    val buildingNumber: String,
    @ColumnInfo(name = "apartment_number")
    val apartmentNumber: String,
    val city: String,
    val postcode: String,
    @ColumnInfo(name = "edge_region")
    val edgeRegion: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Calendar
)
