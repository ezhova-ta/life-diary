package com.example.lifediary.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.lifediary.data.db.entities.DbEntity
import com.example.lifediary.data.domain.MemorableDate
import java.util.*

fun Int.createStringWithPlusOrMinusSign(): String {
	if(this < 0 || this == 0) return toString()
	return String.format("+%d", this)
}

fun <T : DbEntity<R>, R> LiveData<List<T>>.toDomain(): LiveData<List<R>> {
	return map { entityList ->
		entityList.map { entity ->
			entity.toDomain()
		}
	}
}

fun List<String>.isAllItemsBlank(): Boolean {
	forEach { if(it.isNotBlank()) return false }
	return true
}

fun List<MemorableDate>.sortBasedToday(): List<MemorableDate> {
	if(isEmpty()) return this

	val nearestDateInThisMonth = find { it.monthNumber == getNowMonthNumber() && it.dayNumber >= getNowDayNumber() }
	nearestDateInThisMonth?.let { return splitAndSwap(indexOf(it)) }

	val nearestDate = find { it.monthNumber > getNowMonthNumber() }
	nearestDate?.let { return splitAndSwap(indexOf(it)) }

	return this
}

private fun <T> List<T>.splitAndSwap(splitIndex: Int): List<T> {
	val head = subList(0, splitIndex)
	val tail = subList(splitIndex, lastIndex + 1)
	return tail.plus(head)
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

fun String.startWithCapitalLetter(): String {
	return when(length) {
		0 -> this
		1 -> toUpperCase(Locale.getDefault())
		else -> {
			val firstLetterInUpperCase = substring(0, 1).toUpperCase(Locale.getDefault())
			val otherLettersInLowercase = substring(1).toLowerCase(Locale.getDefault())
			firstLetterInUpperCase + otherLettersInLowercase
		}
	}
}