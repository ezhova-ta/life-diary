package com.example.lifediary.data.utils

fun getMetersPerSecondFromKmPerHour(kmPerHour: Double): Double {
	return kmPerHour / 3.6
}

fun getKmPerHourFromMetersPerSecond(metersPerSecond: Double): Double {
	return metersPerSecond * 3.6
}