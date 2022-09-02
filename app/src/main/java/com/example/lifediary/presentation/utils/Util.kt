package com.example.lifediary.presentation.utils

import androidx.lifecycle.LiveData
import com.example.lifediary.domain.models.*
import com.example.lifediary.domain.utils.CalendarBuilder
import com.example.lifediary.domain.utils.getYear
import com.example.lifediary.domain.utils.searchers.PostAddressListItemSearcher
import com.example.lifediary.domain.utils.toDateString
import com.example.lifediary.presentation.utils.dates.toDateString
import com.example.lifediary.presentation.utils.livedata.TwoSourceLiveData
import java.util.*

val WeatherForecastItem.maxTemperatureString
	get() = maxTemperature.createStringWithPlusOrMinusSign()

val WeatherForecastItem.minTemperatureString
	get() = minTemperature.createStringWithPlusOrMinusSign()

val WeatherForecastItem.formattedIconUrl
	get() = iconUrl.addHttpsToStart()

val Weather.temperatureString
	get() = temperature.createStringWithPlusOrMinusSign()

val Weather.temperatureFeelsLikeString
	get() = temperatureFeelsLike.createStringWithPlusOrMinusSign()

private fun Int.createStringWithPlusOrMinusSign(): String {
	if(this < 0 || this == 0) return toString()
	return "+$this"
}

val Weather.formattedIconUrl
	get() = iconUrl?.addHttpsToStart()

private fun String.addHttpsToStart(): String {
	return "https:$this"
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

fun MemorableDate.toOutputFormattedString(): String {
	year?.let { return Day(dayNumber, monthNumber, it).toDateString() }
	val thisYear = CalendarBuilder().build().getYear()
	return Day(dayNumber, monthNumber, thisYear).toDateString(false)
}

fun String.startWithCapitalLetter(): String {
	return when(length) {
		0 -> this
		1 -> uppercase(Locale.getDefault())
		else -> {
			val firstLetterInUpperCase = substring(0, 1).uppercase(Locale.getDefault())
			val otherLettersInLowercase = substring(1).lowercase(Locale.getDefault())
			firstLetterInUpperCase + otherLettersInLowercase
		}
	}
}

fun List<String>.isAllItemsBlank(): Boolean {
	return all { it.isBlank() }
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

fun LiveData<List<PostAddress>>.search(query: LiveData<String?>): LiveData<List<PostAddress>> {
	return TwoSourceLiveData<List<PostAddress>, String?, List<PostAddress>>(
		this,
		query
	) { originalList, searchQuery ->
		originalList ?: return@TwoSourceLiveData emptyList()
		searchQuery ?: return@TwoSourceLiveData originalList
		PostAddressListItemSearcher().search(originalList, searchQuery)
	}
}