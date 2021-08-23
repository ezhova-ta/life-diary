package com.example.lifediary.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// TODO Correct class  name
// TODO Javadoc
@Parcelize
data class Day(
	val dayNumber: Int,
	val monthNumber: Int,
	val year: Int
) : Parcelable
