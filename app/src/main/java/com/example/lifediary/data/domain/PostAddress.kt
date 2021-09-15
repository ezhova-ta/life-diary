package com.example.lifediary.data.domain

import java.util.*

data class PostAddress(
	var id: Long? = null,
	val name: String,
	val street: String,
	val buildingNumber: String,
	val apartmentNumber: String,
	val city: String,
	val postcode: String,
	val edgeRegion: String,
	var createdAt: Calendar = Calendar.getInstance()
) {
	override fun toString() =
		String.format(
			"%s%s%s%s%s%s%s",
			name,
			getFormattedStringFor(street),
			getFormattedStringFor(buildingNumber),
			getFormattedStringFor(apartmentNumber),
			getFormattedStringFor(city),
			getFormattedStringFor(edgeRegion),
			getFormattedStringFor(postcode)
		)

	private fun getFormattedStringFor(addressPart: String) =
		if(addressPart.isBlank()) {
			""
		} else {
			" $addressPart"
		}
}