package com.example.lifediary.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.lifediary.data.db.converters.CalendarConverter
import com.example.lifediary.data.domain.PostAddress
import java.util.*

@Entity(tableName = "post_address")
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
) {
    companion object {
        fun fromDomain(address: PostAddress): PostAddressEntity {
            return PostAddressEntity(
                id = address.id,
                name = address.name,
                street = address.street,
                buildingNumber = address.buildingNumber,
                apartmentNumber = address.apartmentNumber,
                city = address.city,
                postcode = address.postcode,
                edgeRegion = address.edgeRegion,
                createdAt = address.createdAt
            )
        }
    }

    fun toDomain(): PostAddress {
        return PostAddress(
            id = id,
            name = name,
            street = street,
            buildingNumber = buildingNumber,
            apartmentNumber = apartmentNumber,
            city = city,
            postcode = postcode,
            edgeRegion = edgeRegion,
            createdAt = createdAt
        )
    }
}
