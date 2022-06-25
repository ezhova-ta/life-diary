package com.example.lifediary.presentation.utils

import com.example.lifediary.BuildConfig
import com.example.lifediary.domain.models.*
import com.example.lifediary.domain.utils.*
import com.example.lifediary.presentation.utils.dates.toDateString
import java.util.*

fun Int.createStringWithPlusOrMinusSign(): String {
	if(this < 0 || this == 0) return toString()
	return "+$this"
}

fun String.startWithCapitalLetter(): String {
	return when(length) {
		0 -> this
		1 -> uppercase(Locale.getDefault())
		else -> {
			val firstLetterInUpperCase = substring(0, 1).uppercase(Locale.getDefault())
			val otherLettersInLowercase = substring(1).uppercase(Locale.getDefault())
			firstLetterInUpperCase + otherLettersInLowercase
		}
	}
}

fun Location.getFormattedCoordinatesString() =
	"$lat, $lon"

fun MenstruationPeriod.toOutputFormattedString(): String {
	return "${startDate.toDateString()} - ${endDate.toDateString()}"
}

fun PostAddress.toOutputFormattedString() =
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

private fun PostAddress.getFormattedStringFor(addressPart: String) =
	if(addressPart.isBlank()) {
		""
	} else {
		" $addressPart"
	}

fun getDateString(dayNumber: Int, monthNumber: Int, year: Int? = null): String {
	return if(year == null) {
		val thisYear = CalendarBuilder().build().getYear()
		Day(dayNumber, monthNumber, thisYear).toDateString(false)
	} else {
		Day(dayNumber, monthNumber, year).toDateString()
	}
}

val WeatherForecastTemperature.dayString
	get() = day.createStringWithPlusOrMinusSign()

val WeatherForecastTemperature.nightString
	get() = night.createStringWithPlusOrMinusSign()

val WeatherForecastTemperature.morningString
	get() = morning.createStringWithPlusOrMinusSign()

val WeatherForecastTemperature.minString
	get() = min?.createStringWithPlusOrMinusSign()

val WeatherForecastTemperature.maxString
	get() = max?.createStringWithPlusOrMinusSign()

val Weather.iconUrl
	get() = "${BuildConfig.WEATHER_API_ICON_URL}$icon@2x.png"

val WeatherDescription.iconUrl
	get() = "${BuildConfig.WEATHER_API_ICON_URL}$icon@2x.png"

val Weather.temperatureString
	get() = temperature.createStringWithPlusOrMinusSign()

val Weather.temperatureFeelsLikeString
	get() = temperatureFeelsLike.createStringWithPlusOrMinusSign()

fun List<String>.isAllItemsBlank(): Boolean {
	forEach { if(it.isNotBlank()) return false }
	return true
}

/**
 * Returns a new array that starts at the specified element, followed by the elements
 * that were after it in the original array, and then the elements that were before
 * it in the original array (in the same order). If a permutation of elements is not required,
 * or if the array does not contain the specified element, the original array will be returned
 *
 * @param element element at which the returned array should start
 * @return new array that starts at the specified element
 */
fun <T> Array<T>.startFrom(element: T): Array<T> {
	val indexOfElement = indexOf(element)
	if(isEmpty() || hasOneElement() || element == first() ||  indexOfElement == -1) return this
	return splitAndSwap(indexOfElement)
}

private fun <T> Array<T>.hasOneElement(): Boolean {
	return count() == 1
}

private fun <T> Array<T>.splitAndSwap(splitIndex: Int): Array<T> {
	val head = sliceArray(0 until splitIndex)
	val tail = sliceArray(splitIndex..indices.last)
	return tail + head
}